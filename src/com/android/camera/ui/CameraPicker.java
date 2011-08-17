/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera.ui;

import com.android.camera.CameraPreference.OnPreferenceChangedListener;
import com.android.camera.ListPreference;
import com.android.camera.R;

import android.content.Context;
import android.hardware.Camera.CameraInfo;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * A view for switching the front/back camera.
 */
public class CameraPicker extends RotateImageView implements View.OnClickListener {
    private OnPreferenceChangedListener mListener;
    private ListPreference mPreference;
    private CharSequence[] mCameras;
    private int mCameraFacing;

    public CameraPicker(Context context) {
        super(context);
    }

    public CameraPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListener(OnPreferenceChangedListener listener) {
        mListener = listener;
    }

    public void initialize(ListPreference pref) {
        mPreference = pref;
        mCameras = pref.getEntryValues();
        if (mCameras == null) return;
        setOnClickListener(this);
        String cameraId = pref.getValue();
        setVisibility(View.VISIBLE);
        if (mCameras[CameraInfo.CAMERA_FACING_FRONT].equals(cameraId)) {
            mCameraFacing = CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCameraFacing = CameraInfo.CAMERA_FACING_BACK;
        }
    }

    public void setCameraPickerIcon() {
        setImageResource((mCameraFacing == CameraInfo.CAMERA_FACING_BACK)
                ? R.drawable.ic_rotate_camera_facing_back
                : R.drawable.ic_rotate_camera_facing_forward);
    }

    @Override
    public void onClick(View v) {
        if (mCameras == null) return;
        int newCameraIndex = (mCameraFacing == CameraInfo.CAMERA_FACING_BACK)
                ? CameraInfo.CAMERA_FACING_FRONT
                : CameraInfo.CAMERA_FACING_BACK;
        mCameraFacing = newCameraIndex;
        setCameraPickerIcon();
        mPreference.setValue((String) mCameras[mCameraFacing]);
        mListener.onSharedPreferenceChanged();
    }
}
