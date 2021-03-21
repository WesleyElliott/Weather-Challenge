package com.wesleyelliott.weather.ui.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class BoxState {
    Collapsed,
    Expanded,
}

internal interface AccordionLayoutScopeContentFactory {
    fun getContent(index: Int, scope: AccordionLayoutScope): @Composable () -> Unit
}

/**
 * Receiver scope which is used by the [AccordionLayout].
 */
interface AccordionLayoutScope {

    /**
     * Add a single item to the accordion layout
     * @param content content of the item. This has the [BoxState] as a receiver to allow different
     * contents based on what [BoxState] it is in.
     */
    fun item(content: @Composable (AccordionLayoutScope.(BoxState) -> Unit))

    /**
     * Show the next item in the accordion layout
     */
    fun next()
}

/**
 * Represents the actual content of the accordion layout
 */
private class AccordionLayoutContent(
    val content: AccordionLayoutScope.(index: Int) -> @Composable () -> Unit
)

/**
 * A state object that can be used to observe the current item of the accordion layout, and can be
 * used to control the position.
 * Should be used with [rememberAccordionState] in order to preserve the state across config
 * changes.
 *
 * @param initialCurrentItem the initial current position of the accordion layout
 */
class AccordionLayoutState(
    initialCurrentItem: Int = 0
) {
    /**
     * The current item to show in the expanded state
     */
    val currentItem = mutableStateOf(initialCurrentItem)

    companion object {
        /**
         * Saver implementation to save and restore the current position across config changes
         */
        val Saver: Saver<AccordionLayoutState, *> = Saver(
            save = {
                it.currentItem.value
            },
            restore = {
                AccordionLayoutState(
                    initialCurrentItem = it
                )
            }
        )
    }
}

/**
 * Implementation of the [AccordionLayoutScope] to manage the list of accordion layouts in the
 * [AccordionLayout].
 * @param maxHeight the maximum height of the container to expand to
 * @param maxWidth the maximum width of the container to expand to
 * @param collapsedSize the size of the accordion layout when collapsed
 * @param isVertical if the layout should expand vertically, or horizontally
 * @param accordionLayoutState the state used to control the accordion layout position
 */
private class AccordionLayoutScopeImpl(
    private val maxHeight: Dp,
    private val maxWidth: Dp,
    private val collapsedSize: Dp,
    private val isVertical: Boolean = true,
    private val accordionLayoutState: AccordionLayoutState
): AccordionLayoutScope, AccordionLayoutScopeContentFactory {

    /**
     * List of accordion items to show on the screen
     */
    private val items = mutableListOf<AccordionLayoutContent>()
    val totalSize get() = items.size

    /**
     * The current item to show in the expanded state
     * This is just a backing field, the real state is controlled in the [accordionLayoutState]
     */
    private var currentItem: Int
        get() = accordionLayoutState.currentItem.value
        set(value) {
            accordionLayoutState.currentItem.value = value
        }

    override fun next() {
        currentItem += 1
    }

    override fun getContent(index: Int, scope: AccordionLayoutScope): @Composable () -> Unit {
        val item = items[index]
        return item.content.invoke(scope, index)
    }

    override fun item(content: @Composable AccordionLayoutScope.(BoxState) -> Unit) {
        items.add(
            AccordionLayoutContent(
                content = { index ->
                    @Composable {
                        /**
                         * Get the current state of this specific accordion layout.
                         * If the current position is bigger than the index, then this view is
                         * [BoxState.Collapsed]
                         */
                        val internalState = when {
                            currentItem <= index -> BoxState.Expanded
                            else -> BoxState.Collapsed
                        }

                        val maxSize = if (isVertical) maxHeight.value else maxWidth.value

                        /**
                         * Create a transition from the max height or width (based on if the layout
                         * is vertical or horizontal) to the collapsed size (and vice-versa)
                         */
                        val transition = updateTransition(targetState = internalState)
                        val height = transition.animateFloat { state ->
                            when (state) {
                                BoxState.Collapsed -> collapsedSize.value
                                BoxState.Expanded -> maxSize
                            }
                        }

                        /**
                         * Base modifier to apply the click action to expand the accordion
                         * to the specific index.
                         */
                        var modifier = Modifier.clickable(
                            enabled = internalState == BoxState.Collapsed,
                            onClick = {
                                currentItem = index
                            }
                        )

                        /**
                         * Apply a width or height modifier based on if the layout is vertical or
                         * horizontal.
                         */
                        modifier = if (isVertical) {
                            modifier.fillMaxWidth().height(height.value.dp)
                        } else {
                            modifier.fillMaxHeight().width(height.value.dp)
                        }

                        /**
                         * The [Box] used to render the content at the specific [height] or [width],
                         * which is animated when the internal state changes.
                         *
                         * This is also clickable to allow re-expanding a collapsed item
                         * NOTE: expanding an item also expands all the items _after_ that item
                         */
                        Box(
                            modifier = modifier
                        ) {
                            content(internalState)
                        }
                    }
                }
            )
        )
    }
}

@Composable
fun rememberAccordionState(initialCurrentItem: Int = 0): AccordionLayoutState {
    return rememberSaveable(saver = AccordionLayoutState.Saver) {
        AccordionLayoutState(initialCurrentItem)
    }
}

/**
 * An accordion style column that shows each item as a full-height composable that can be collapsed
 * to a specific height.
 *
 * The [content] block defines a DSL which allows you to emit different items of the accordion.
 * These are laid out in the same order as the items are emitted, and can be controlled via the
 * [AccordionLayoutScope.next] function.
 *
 * @param modifier the modifier to apply to this layout
 * @param accordionLayoutState a state that can be used by consumers to control the accordion
 * @param collapsedSize the height of the accordion layout when collapsed
 * @param isVertical specify if the layout should be vertical or horizontal
 * @param content a block to describe the accordion items using the [AccordionLayoutScope.item] method
 */
@Composable
fun AccordionLayout(
    modifier: Modifier = Modifier,
    accordionLayoutState: AccordionLayoutState = rememberAccordionState(),
    collapsedSize: Dp = 150.dp,
    isVertical: Boolean = true,
    content: AccordionLayoutScope.() -> Unit
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val scope = AccordionLayoutScopeImpl(
            maxHeight = maxHeight,
            maxWidth = maxWidth,
            collapsedSize = collapsedSize,
            isVertical = isVertical,
            accordionLayoutState = accordionLayoutState
        )
        scope.apply(content)

        AccordionLayoutImpl(
            modifier = modifier,
            itemCount = scope.totalSize,
            isVertical = isVertical,
            scope = scope,
            scopedFactory = scope
        )
    }

}

@Composable
private fun AccordionLayoutImpl(
    modifier: Modifier = Modifier,
    itemCount: Int,
    isVertical: Boolean = true,
    scope: AccordionLayoutScope,
    scopedFactory: AccordionLayoutScopeContentFactory
) {
    if (isVertical) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            for (i in 0 until itemCount) {
                scopedFactory.getContent(i, scope).invoke()
            }
        }
    } else {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            for (i in 0 until itemCount) {
                scopedFactory.getContent(i, scope).invoke()
            }
        }
    }
}
