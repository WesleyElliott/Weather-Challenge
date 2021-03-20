package com.wesleyelliott.weather.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.math.ceil

/**
 * Represents the actual composable content of the grid item
 */
private class GridLayoutContent(
    val content: GridLayoutScope.(index: Int) -> @Composable () -> Unit
)

/**
 * Receiver scope used by the [GridLayout] to add items
 */
interface GridLayoutScope {
    fun item(content: @Composable GridLayoutScope.() -> Unit)
    fun items(count: Int, itemContent: @Composable GridLayoutScope.(index: Int) -> Unit)
}

/**
 * Add a list of items
 *
 * @param items the list
 * @param itemContent the content of a single item
 */
inline fun <T> GridLayoutScope.items(
    items: List<T>,
    crossinline itemContent: @Composable GridLayoutScope.(item: T) -> Unit
) = items(items.size) {
    itemContent(items[it])
}

/**
 * Implementation of the [GridLayoutScope] that manages a list of items that are rendered to the
 * [GridLayout].
 */
private class GridLayoutScopeImpl : GridLayoutScope {

    private val items = mutableListOf<GridLayoutContent>()
    val totalSize: Int
        get() = items.size

    override fun item(content: @Composable GridLayoutScope.() -> Unit) {
        items.add(
            GridLayoutContent(
                content = {
                    @Composable {
                        content()
                    }
                }
            )
        )
    }

    override fun items(count: Int, itemContent: @Composable GridLayoutScope.(index: Int) -> Unit) {
        for (i in 0 until count) {
            items.add(
                GridLayoutContent(
                    content = {
                        @Composable{
                            itemContent(i)
                        }
                    }
                )
            )
        }
    }

    fun getContent(index: Int):  @Composable () -> Unit  {
        val item = items[index]
        return item.content.invoke(this, index)
    }
}

/**
 * Naive grid layout that uses the power of the [Column] and [Row] composables to layout a grid of
 * items. This is done instead of using a manual layout with placing items, as row items can be
 * aligned based on the [Arrangement] and [Alignment] options already supported by columns and rows.
 *
 * @param modifier the modifier to apply to this layout
 * @param columnCount the number of columns to populate the grid by. Depending on the amount if
 *                    items added in the content block, a dynamic number of rows will be added
 * @param verticalArrangement the vertical arrangement of the main grid
 * @param horizontalAlignment the horizontal alignment of the main grid
 * @param horizontalArrangement the horizontal arrangement of the children of the grid (each item)
 */
@Composable
fun GridLayout(
    modifier: Modifier = Modifier,
    columnCount: Int,
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceEvenly,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    content: GridLayoutScope.() -> Unit
) {
    val scope = GridLayoutScopeImpl()
    scope.apply(content)

    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        val rowCount = ceil(scope.totalSize / columnCount.toFloat()).toInt()
        var index = 0
        for (i in 0 until rowCount) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = horizontalArrangement,
            ) {
                for (j in 0 until columnCount) {
                    if (index >= scope.totalSize) {
                        break
                    }
                    scope.getContent(index).invoke()
                    index++
                }
            }
        }
    }
}
