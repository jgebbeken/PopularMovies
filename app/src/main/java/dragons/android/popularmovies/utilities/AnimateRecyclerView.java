package dragons.android.popularmovies.utilities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

/*Based on
https://gist.github.com/Musenkishi/8df1ab549857756098ba
Credit to Freddie (Musenkishi) Lust-Hed

with calculations based on google's own gridview framework on how to lay down columns and rows which
are featured in lines 562-565 of this code:
https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/widget/GridView.java#564

Purpose of this code is to combine the Grid Layout Manger with the Layout Animation. RecyclerView
is a generic class and needs to be extended. By not extending it will causes a runtime exception.

*/



public class AnimateRecyclerView extends RecyclerView {

    public AnimateRecyclerView(Context context) {
        super(context);
    }

    public AnimateRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimateRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void attachLayoutAnimationParameters(View view, ViewGroup.LayoutParams layoutParams,
                                                 int index, int count) {

        final LayoutManager layoutManager = getLayoutManager();

        if (getAdapter() != null && layoutManager instanceof GridLayoutManager){



            GridLayoutAnimationController.AnimationParameters animationParameters =

                    (GridLayoutAnimationController.AnimationParameters) layoutParams.layoutAnimationParameters;



            if (animationParameters == null) {

                // If there are no animation parameters, create new once and attach them to

                // the LayoutParams.

                animationParameters = new GridLayoutAnimationController.AnimationParameters();

                layoutParams.layoutAnimationParameters = animationParameters;

            }



            // Set the number of items in the RecyclerView and the index of this item

            animationParameters.count = count;
            animationParameters.index = index;



            // We need to know how many of the columns and rows are being requested.
            final int columns = ((GridLayoutManager) layoutManager).getSpanCount();

            animationParameters.columnsCount = columns;
            animationParameters.rowsCount = count / columns;



            // The delay with columns and rows so they don't animate at the same time.

            final int staggeredIndex = count - 1 - index;
            animationParameters.column = columns - 1 - (staggeredIndex % columns);
            animationParameters.row = animationParameters.rowsCount - 1 - staggeredIndex / columns;



        } else {

            // It's not a gridlayout so we are going to ignore it.
            super.attachLayoutAnimationParameters(view, layoutParams, index, count);

        }

    }






}
