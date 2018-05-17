package dragons.android.popularmovies.Utilities;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jgebbeken on 5/15/2018.
 */

public class ColumnSpacing extends RecyclerView.ItemDecoration {
    private int space;

    public ColumnSpacing(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView parent, RecyclerView.State state) {
        rect.bottom = space;

        if(parent.getChildLayoutPosition(view)%2 == 0) {
            rect.left = space;
            rect.right = space;

        } else {
            rect.top = space;
        }
    }
}
