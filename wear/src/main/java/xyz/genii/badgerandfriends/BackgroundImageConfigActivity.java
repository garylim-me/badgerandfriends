package xyz.genii.badgerandfriends;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class BackgroundImageConfigActivity extends Activity implements
        WearableListView.ClickListener, WearableListView.OnScrollListener {



    /*

    private static final String TAG = "DigitalWatchFaceConfig";

    private WearableListView mWearableConfigListView;
    private ConfigurationAdapter mAdapter;

    private GoogleApiClient mGoogleApiClient;
    private TextView mHeader;
    */

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_image_config);

        mHeader = (TextView) findViewById(R.id.header);
        WearableListView listView = (WearableListView) findViewById(R.id.color_picker);
        BoxInsetLayout content = (BoxInsetLayout) findViewById(R.id.content);
        // BoxInsetLayout adds padding by default on round devices. Add some on square devices.
        content.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                if (!insets.isRound()) {
                    v.setPaddingRelative(
                            (int) getResources().getDimensionPixelSize(R.dimen.content_padding_start),
                            v.getPaddingTop(),
                            v.getPaddingEnd(),
                            v.getPaddingBottom());
                }
                return v.onApplyWindowInsets(insets);
            }
        });

        mAdapter = new ConfigurationAdapter(getApplicationContext(), getComplicationItems(getApplicationContext()));

        mWearableConfigListView = (WearableListView) findViewById(R.id.image_picker);
        mWearableConfigListView.setAdapter(mAdapter);
        mWearableConfigListView.setClickListener(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        if (Log.isLoggable(TAG, Log.DEBUG)) {
                            Log.d(TAG, "onConnected: " + connectionHint);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        if (Log.isLoggable(TAG, Log.DEBUG)) {
                            Log.d(TAG, "onConnectionSuspended: " + cause);
                        }
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        if (Log.isLoggable(TAG, Log.DEBUG)) {
                            Log.d(TAG, "onConnectionFailed: " + result);
                        }
                    }
                })
                .addApi(Wearable.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override // WearableListView.ClickListener
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.e(TAG,"clicked!");

        Integer tag = (Integer) viewHolder.itemView.getTag();
        ImageItem imageItem = mAdapter.getItem(tag);
        updateConfigDataItem(imageItem.getImage());
        finish();
    }

    @Override // WearableListView.ClickListener
    public void onTopEmptyRegionClick() {}

    @Override // WearableListView.OnScrollListener
    public void onScroll(int scroll) {}


    @Override // WearableListView.OnScrollListener
    public void onAbsoluteScrollChange(int scroll) {
        float newTranslation = Math.min(-scroll, 0);
        mHeader.setTranslationY(newTranslation);
    }

    @Override // WearableListView.OnScrollListener
    public void onScrollStateChanged(int scrollState) {}

    @Override // WearableListView.OnScrollListener
    public void onCentralPositionChanged(int centralPosition) {}

    private void updateConfigDataItem(final String backgroundImage) {
        DataMap configKeysToOverwrite = new DataMap();
        configKeysToOverwrite.putString(BadgerAndFriendsUtil.KEY_BACKGROUND_IMAGE,
                backgroundImage);
        BadgerAndFriendsUtil.overwriteKeysInConfigDataMap(mGoogleApiClient, configKeysToOverwrite);
        Log.e(TAG,"config overwritten!");
    }*/

    /*
    private static class ConfigurationAdapter extends WearableListView.Adapter {

        private Context mContext;
        private final LayoutInflater mInflater;
        private List<ImageItem> mItems;

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(new ImageItem(parent.getContext()));
        }


        public ConfigurationAdapter (Context context, List<ImageItem> items) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mItems = items;
        }

        // Provides a reference to the type of views you're using
        public static class ItemViewHolder extends WearableListView.ViewHolder {
            private ImageView iconImageView;
            private TextView textView;
            public ItemViewHolder(View itemView) {
                super(itemView);
                iconImageView = (ImageView) itemView.findViewById(R.id.icon);
                textView = (TextView) itemView.findViewById(R.id.image_name);
            }
        }



        private static class ItemViewHolder extends WearableListView.ViewHolder {
            private final ImageItem mImageItem;

            public ItemViewHolder(ImageItem imageItem) {
                super(imageItem);
                mImageItem = imageItem;
            }
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // Inflate custom layout for list items.
            return new ItemViewHolder(
                    mInflater.inflate(R.layout.activity_background_image_list_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {

            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            ImageView imageView = itemHolder.iconImageView;
            imageView.setImageDrawable(mItems.get(position).icon);

            TextView textView = itemHolder.textView;
            textView.setText(mItems.get(position).title.toString());

            Log.e(TAG,"text set to" + mItems.get(position).title);

            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public ImageItem getItem(int position) {
            return mItems.get(position);
        }
    }





    private List<ImageItem> getComplicationItems(Context context) {
        ComponentName watchFace = new ComponentName(
                getApplicationContext(), BadgerAndFriends.class);

        String[] complicationNames =
                getResources().getStringArray(R.array.image_array);

        TypedArray icons = getResources().obtainTypedArray(R.array.images_icons);

        List<ImageItem> items = new ArrayList<>();
        for (int i = 0; i < complicationNames.length; i++) {

            items.add(new ImageItem(context, watchFace,
                    icons.getDrawable(i),
                    complicationNames[i]));

        }
        return items;
    }

*/

















    private static final String TAG = "DigitalWatchFaceConfig";

    private GoogleApiClient mGoogleApiClient;
    private TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_image_config);

        mHeader = (TextView) findViewById(R.id.image_config_header);
        WearableListView listView = (WearableListView) findViewById(R.id.image_picker);
        BoxInsetLayout content = (BoxInsetLayout) findViewById(R.id.image_config_content);
        // BoxInsetLayout adds padding by default on round devices. Add some on square devices.
        content.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                if (!insets.isRound()) {
                    v.setPaddingRelative(
                            (int) getResources().getDimensionPixelSize(R.dimen.content_padding_start),
                            v.getPaddingTop(),
                            v.getPaddingEnd(),
                            v.getPaddingBottom());
                }
                return v.onApplyWindowInsets(insets);
            }
        });

        listView.setHasFixedSize(true);
        listView.setClickListener(this);
        listView.addOnScrollListener(this);

        String[] configs = getResources().getStringArray(R.array.image_array);
        TypedArray icons = getResources().obtainTypedArray(R.array.images_icons);
        listView.setAdapter(new ConfigListAdapter(configs,icons));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        if (Log.isLoggable(TAG, Log.DEBUG)) {
                            Log.d(TAG, "onConnected: " + connectionHint);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        if (Log.isLoggable(TAG, Log.DEBUG)) {
                            Log.d(TAG, "onConnectionSuspended: " + cause);
                        }
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        if (Log.isLoggable(TAG, Log.DEBUG)) {
                            Log.d(TAG, "onConnectionFailed: " + result);
                        }
                    }
                })
                .addApi(Wearable.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override // WearableListView.ClickListener
    public void onClick(WearableListView.ViewHolder viewHolder) {

        /*
        //ORIGINAL:
        ConfigItemViewHolder configItemViewHolder = (ConfigItemViewHolder) viewHolder;
        updateConfigDataItem(configItemViewHolder.mConfigItem.getColor());
        //finish();

        //GARY's:
        Intent intent;
        switch(configItemViewHolder.mConfigItem.getVerticalScrollbarPosition()) {
            case 0:
                intent = new Intent(this.getBaseContext(), BackgroundImageConfigActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this.getBaseContext(), BackgroundOptions.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this.getBaseContext(), BackgroundOptions.class);
                startActivity(intent);
                break;
        }
        */

        Log.e(TAG,"clicked!");

        ConfigItemViewHolder configItemViewHolder = (ConfigItemViewHolder) viewHolder;
        updateConfigDataItem(configItemViewHolder.mConfigItem.getImage());
        finish();

    }

    @Override // WearableListView.ClickListener
    public void onTopEmptyRegionClick() {}

    @Override // WearableListView.OnScrollListener
    public void onScroll(int scroll) {}

    @Override // WearableListView.OnScrollListener
    public void onAbsoluteScrollChange(int scroll) {
        float newTranslation = Math.min(-scroll, 0);
        mHeader.setTranslationY(newTranslation);
    }

    @Override // WearableListView.OnScrollListener
    public void onScrollStateChanged(int scrollState) {}

    @Override // WearableListView.OnScrollListener
    public void onCentralPositionChanged(int centralPosition) {}



    private void updateConfigDataItem(final String backgroundImage) {
        DataMap configKeysToOverwrite = new DataMap();
        configKeysToOverwrite.putString(BadgerAndFriendsUtil.KEY_BACKGROUND_IMAGE,
                backgroundImage);
        BadgerAndFriendsUtil.overwriteKeysInConfigDataMap(mGoogleApiClient, configKeysToOverwrite);
        Log.e(TAG,"config overwritten!");
    }

    /*
    private void updateConfigDataItem(final int backgroundColor) {
        DataMap configKeysToOverwrite = new DataMap();

        //G: Irrelevant
        //configKeysToOverwrite.putInt(DigitalWatchFaceUtil.KEY_BACKGROUND_COLOR,
        //        backgroundColor);
        //DigitalWatchFaceUtil.overwriteKeysInConfigDataMap(mGoogleApiClient, configKeysToOverwrite);
    }*/

    private class ConfigListAdapter extends WearableListView.Adapter {
        private final String[] mConfigs;
        private final TypedArray mIcons;

        public ConfigListAdapter(String[] configs, TypedArray icons) {
            mConfigs = configs;
            mIcons = icons;
        }

        @Override
        public ConfigItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ConfigItemViewHolder(new ImageItem(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            ConfigItemViewHolder configItemViewHolder = (ConfigItemViewHolder) holder;
            String colorName = mConfigs[position];
            Drawable icon = mIcons.getDrawable(position);
            configItemViewHolder.mConfigItem.setConfigName(colorName,icon);

            RecyclerView.LayoutParams layoutParams =
                    new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            int colorPickerItemMargin = (int) getResources()
                    .getDimension(R.dimen.digital_config_color_picker_item_margin);
            // Add margins to first and last item to make it possible for user to tap on them.
            if (position == 0) {
                layoutParams.setMargins(0, colorPickerItemMargin, 0, 0);
            } else if (position == mConfigs.length - 1) {
                layoutParams.setMargins(0, 0, 0, colorPickerItemMargin);
            } else {
                layoutParams.setMargins(0, 0, 0, 0);
            }
            configItemViewHolder.itemView.setLayoutParams(layoutParams);
        }

        @Override
        public int getItemCount() {
            return mConfigs.length;
        }
    }

    /** The layout of a color item including image and label. */
    private static class ImageItem extends LinearLayout implements
            WearableListView.OnCenterProximityListener {

        /*
        ComponentName watchFace;
        Drawable icon;
        String title;

        public ImageItem(Context context, ComponentName watchFace, Drawable icon, String title) {
            super(context);

            this.watchFace = watchFace;
            this.icon = icon;
            this.title = title;
        }
        */

        /** The duration of the expand/shrink animation. */
        private static final int ANIMATION_DURATION_MS = 150;
        /** The ratio for the size of a circle in shrink state. */
        private static final float SHRINK_CIRCLE_RATIO = .75f;

        private static final float SHRINK_LABEL_ALPHA = .5f;
        private static final float EXPAND_LABEL_ALPHA = 1f;

        private static final int SHRINK_IMAGE_ALPHA = 70;
        private static final int EXPAND_IMAGE_ALPHA = 255;

        private final TextView mLabel;
        private final ImageView mColor;

        private final float mExpandCircleRadius;
        private final float mShrinkCircleRadius;

        //private final ObjectAnimator mExpandCircleAnimatorX;
        //private final ObjectAnimator mExpandCircleAnimatorY;
        private final ObjectAnimator mExpandCircleAnimator;
        private final ObjectAnimator mExpandLabelAnimator;
        private final AnimatorSet mExpandAnimator;

        //private final ObjectAnimator mShrinkCircleAnimatorX;
        //private final ObjectAnimator mShrinkCircleAnimatorY;
        private final ObjectAnimator mShrinkCircleAnimator;
        private final ObjectAnimator mShrinkLabelAnimator;
        private final AnimatorSet mShrinkAnimator;



        public ImageItem(Context context) {
            super(context);
            View.inflate(context, R.layout.activity_background_image_list_item, this);

            mLabel = (TextView) findViewById(R.id.image_name);
            mColor = (ImageView) findViewById(R.id.icon);

            mExpandCircleRadius = mColor.getMaxWidth()/2;
            mShrinkCircleRadius = mExpandCircleRadius * SHRINK_CIRCLE_RATIO;

            mShrinkCircleAnimator = ObjectAnimator.ofFloat(mColor, "imageAlpha",
                    EXPAND_IMAGE_ALPHA, SHRINK_IMAGE_ALPHA);
            //mShrinkCircleAnimatorX = ObjectAnimator.ofFloat(mColor, "scaleX",
            //        mExpandCircleRadius*2, mShrinkCircleRadius*2);
            //mShrinkCircleAnimatorY = ObjectAnimator.ofFloat(mColor, "scaleY",
            //        mExpandCircleRadius*2, mShrinkCircleRadius*2);
            mShrinkLabelAnimator = ObjectAnimator.ofFloat(mLabel, "alpha",
                    EXPAND_LABEL_ALPHA, SHRINK_LABEL_ALPHA);
            mShrinkAnimator = new AnimatorSet().setDuration(ANIMATION_DURATION_MS);
            mShrinkAnimator.playTogether(
                    //mShrinkCircleAnimatorX,
                    //mShrinkCircleAnimatorY,
                    mShrinkCircleAnimator,
                    mShrinkLabelAnimator);

            mExpandCircleAnimator = ObjectAnimator.ofFloat(mColor, "imageAlpha",
                    SHRINK_IMAGE_ALPHA, EXPAND_IMAGE_ALPHA);
            //mExpandCircleAnimatorX = ObjectAnimator.ofFloat(mColor, "scaleX",
            //        mExpandCircleRadius*2, mShrinkCircleRadius*2);
            //mExpandCircleAnimatorY = ObjectAnimator.ofFloat(mColor, "scaleY",
            //        mExpandCircleRadius*2, mShrinkCircleRadius*2);
            mExpandLabelAnimator = ObjectAnimator.ofFloat(mLabel, "alpha",
                    SHRINK_LABEL_ALPHA, EXPAND_LABEL_ALPHA);
            mExpandAnimator = new AnimatorSet().setDuration(ANIMATION_DURATION_MS);
            mExpandAnimator.playTogether(
                    //mExpandCircleAnimatorX,
                    //mExpandCircleAnimatorY,
                    mExpandCircleAnimator,
                    mExpandLabelAnimator);
        }

        /*
        public ImageItem(Context context, ComponentName watchFace, Drawable icon, String title) {
            super(context);

            this.watchFace = watchFace;
            this.icon = icon;
            this.title = title;

            View.inflate(context, R.layout.color_picker_item, this);


            mLabel = (TextView) findViewById(R.id.image_name);
            mColor = (ImageView) findViewById(R.id.icon);

            mExpandCircleRadius = mColor.getMaxWidth()/2;
            mShrinkCircleRadius = mExpandCircleRadius * SHRINK_CIRCLE_RATIO;

            mShrinkCircleAnimator = ObjectAnimator.ofFloat(mColor, "circleRadius",
                    mExpandCircleRadius, mShrinkCircleRadius);
            mShrinkLabelAnimator = ObjectAnimator.ofFloat(mLabel, "alpha",
                    EXPAND_LABEL_ALPHA, SHRINK_LABEL_ALPHA);
            mShrinkAnimator = new AnimatorSet().setDuration(ANIMATION_DURATION_MS);
            mShrinkAnimator.playTogether(mShrinkCircleAnimator, mShrinkLabelAnimator);

            mExpandCircleAnimator = ObjectAnimator.ofFloat(mColor, "circleRadius",
                    mShrinkCircleRadius, mExpandCircleRadius);
            mExpandLabelAnimator = ObjectAnimator.ofFloat(mLabel, "alpha",
                    SHRINK_LABEL_ALPHA, EXPAND_LABEL_ALPHA);
            mExpandAnimator = new AnimatorSet().setDuration(ANIMATION_DURATION_MS);
            mExpandAnimator.playTogether(mExpandCircleAnimator, mExpandLabelAnimator);
        }*/

        @Override
        public void onCenterPosition(boolean animate) {
            if (animate) {
                mShrinkAnimator.cancel();
                mColor.setImageAlpha(EXPAND_IMAGE_ALPHA);
                if (!mExpandAnimator.isRunning()) {
                    //mExpandCircleAnimatorX.setFloatValues(mColor.getMaxWidth()/2, mExpandCircleRadius);
                    //mExpandCircleAnimatorY.setFloatValues(mColor.getMaxWidth()/2, mExpandCircleRadius);
                    mExpandLabelAnimator.setFloatValues(mLabel.getAlpha(), EXPAND_LABEL_ALPHA);
                    mExpandAnimator.start();
                }
            } else {
                mExpandAnimator.cancel();
                mLabel.setAlpha(EXPAND_LABEL_ALPHA);
                mColor.setImageAlpha(EXPAND_IMAGE_ALPHA);
                //mColor.setScaleType(ImageView.ScaleType.FIT_XY);
                //mColor.setScaleX(mExpandCircleRadius*2);
                //mColor.setScaleY(mExpandCircleRadius*2);
                //mColor.setMinimumHeight((int)mExpandCircleRadius*2);
                //mColor.setMinimumWidth((int)mExpandCircleRadius*2);
            }
        }

        @Override
        public void onNonCenterPosition(boolean animate) {
            if (animate) {
                mExpandAnimator.cancel();
                mColor.setImageAlpha(SHRINK_IMAGE_ALPHA);
                if (!mShrinkAnimator.isRunning()) {
                    //mShrinkCircleAnimatorX.setFloatValues(mColor.getMaxWidth()/2, mShrinkCircleRadius);
                    //mShrinkCircleAnimatorY.setFloatValues(mColor.getMaxWidth()/2, mShrinkCircleRadius);
                    mShrinkLabelAnimator.setFloatValues(mLabel.getAlpha(), SHRINK_LABEL_ALPHA);
                    mShrinkAnimator.start();
                }
            } else {
                mShrinkAnimator.cancel();
                mLabel.setAlpha(SHRINK_LABEL_ALPHA);
                mColor.setImageAlpha(SHRINK_IMAGE_ALPHA);
                //mColor.setScaleType(ImageView.ScaleType.FIT_XY);
                //mColor.setScaleX(mShrinkCircleRadius*2);
                //mColor.setScaleY(mShrinkCircleRadius*2);
                //mColor.setMinimumHeight((int)mShrinkCircleRadius*2);
                //mColor.setMinimumWidth((int)mShrinkCircleRadius*2);

            }
        }

        private void setConfigName(String colorName, Drawable icon) {
            mLabel.setText(colorName);
            mColor.setImageDrawable(icon);
        }

        private String getImage() {
            return mLabel.getText().toString();
        }
    }

    private static class ConfigItemViewHolder extends WearableListView.ViewHolder {
        private final ImageItem mConfigItem;

        public ConfigItemViewHolder(ImageItem configItem) {
            super(configItem);
            mConfigItem = configItem;
        }
    }
}