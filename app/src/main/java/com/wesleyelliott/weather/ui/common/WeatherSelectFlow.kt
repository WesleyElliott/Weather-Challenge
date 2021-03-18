package com.wesleyelliott.weather.ui.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class BoxState {
    Collapsed,
    Expanded,
}

internal interface WeatherSelectScopeContentFactory {
    fun getContent(index: Int, scope: WeatherSelectScope): @Composable () -> Unit
}

/**
 * Receiver scope which is used by the [WeatherSelectFlow].
 */
interface WeatherSelectScope {

    /**
     * Add a single item to the accordion view
     * @param content content of the item. This has the [BoxState] as a receiver to allow different
     * contents based on what [BoxState] it is in.
     */
    fun item(content: @Composable (WeatherSelectScope.(BoxState) -> Unit))

    /**
     * Show the next item in the accordion view
     */
    fun next()
}

/**
 * Represents the actual content of the accordion view, which can either be [BoxState.Expanded] or
 * [BoxState.Collapsed].
 *
 * This state can be changed with the [collapse] or [expand] methods
 */
private class WeatherSelectContent(
    val content: WeatherSelectScope.(index: Int) -> @Composable () -> Unit
) {
    private val _currentState = mutableStateOf(BoxState.Expanded)
    val state: State<BoxState> = _currentState

    fun collapse() {
        _currentState.value = BoxState.Collapsed
    }

    fun expand() {
        _currentState.value = BoxState.Expanded
    }
}

/**
 * Implementation of the [WeatherSelectScope] to manage the list of accordion views in the
 * [WeatherSelectFlow].
 * @param maxHeight the maximum height of the container to expand to
 * @param collapsedHeight the height of the accordion view when collapsed
 */
private class WeatherSelectScopeImpl(
    private val maxHeight: Dp,
    private val collapsedHeight: Dp
): WeatherSelectScope, WeatherSelectScopeContentFactory {

    /**
     * List of accordion items to show on the screen
     */
    private val items = mutableListOf<WeatherSelectContent>()
    val totalSize get() = items.size

    /**
     * The current item to show in the expanded state
     */
    private val currentItem = mutableStateOf(0)

    override fun next() {
        val current = currentItem.value.coerceAtMost(totalSize - 1)
        items[current].collapse()
        currentItem.value += 1
    }

    override fun getContent(index: Int, scope: WeatherSelectScope): @Composable () -> Unit {
        val item = items[index]
        return item.content.invoke(scope, index)
    }

    override fun item(content: @Composable WeatherSelectScope.(BoxState) -> Unit) {
        items.add(
            WeatherSelectContent(
                content = { index ->
                    @Composable {
                        /**
                         * Get the current state of this specific accordion view
                         */
                        val internalState = items[index].state.value

                        /**
                         * Create a transition from the max height to the collapsed height (and
                         * vice-versa)
                         */
                        val transition = updateTransition(targetState = internalState)
                        val height = transition.animateFloat { state ->
                            when (state) {
                                BoxState.Collapsed -> collapsedHeight.value
                                BoxState.Expanded -> maxHeight.value
                            }
                        }

                        /**
                         * The [Box] used to render the content at the specific [height], which
                         * is animated when the internal state changes.
                         *
                         * This is also clickable to allow re-expanding a collapsed item
                         * NOTE: expanding an item also expands all the items _after_ that item
                         */
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    enabled = internalState == BoxState.Collapsed,
                                    onClick = {
                                        currentItem.value = index
                                        items
                                            .subList(index, totalSize)
                                            .forEach {
                                                it.expand()
                                            }
                                    }
                                )
                                .height(height.value.dp),
                        ) {
                            content(internalState)
                        }
                    }
                }
            )
        )
    }
}

/**
 * An accordion style column that shows each item as a full-height composable that can be collapsed
 * to a specific height.
 *
 * The [content] block defines a DSL which allows you to emit different items of the accordion.
 * These are laid out in the same order as the items are emitted, and can be controlled via the
 * [WeatherSelectScope.next] function.
 *
 * @param modifier the modifier to apply to this layout
 * @param collapsedHeight the height of the accordion view when collapsed
 * @param content a block to describe the accordion items using the [WeatherSelectScope.item] method
 */
@Composable
fun WeatherSelectFlow(
    modifier: Modifier = Modifier,
    collapsedHeight: Dp = 150.dp,
    content: WeatherSelectScope.() -> Unit
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val scope = WeatherSelectScopeImpl(
            maxHeight = maxHeight,
            collapsedHeight = collapsedHeight
        )
        scope.apply(content)

        WeatherSelectFlowImpl(
            modifier = modifier,
            itemCount = scope.totalSize,
            scope = scope,
            scopedFactory = scope
        )
    }

}

@Composable
private fun WeatherSelectFlowImpl(
    modifier: Modifier = Modifier,
    itemCount: Int,
    scope: WeatherSelectScope,
    scopedFactory: WeatherSelectScopeContentFactory
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        for (i in 0 until itemCount) {
            scopedFactory.getContent(i, scope).invoke()
        }
    }
}