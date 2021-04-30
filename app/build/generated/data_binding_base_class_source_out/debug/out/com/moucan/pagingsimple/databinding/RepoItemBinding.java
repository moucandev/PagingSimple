// Generated by view binder compiler. Do not edit!
package com.moucan.pagingsimple.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.moucan.pagingsimple.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RepoItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView descriptionText;

  @NonNull
  public final TextView nameText;

  @NonNull
  public final TextView starCountText;

  private RepoItemBinding(@NonNull LinearLayout rootView, @NonNull TextView descriptionText,
      @NonNull TextView nameText, @NonNull TextView starCountText) {
    this.rootView = rootView;
    this.descriptionText = descriptionText;
    this.nameText = nameText;
    this.starCountText = starCountText;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RepoItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RepoItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.repo_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RepoItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.description_text;
      TextView descriptionText = rootView.findViewById(id);
      if (descriptionText == null) {
        break missingId;
      }

      id = R.id.name_text;
      TextView nameText = rootView.findViewById(id);
      if (nameText == null) {
        break missingId;
      }

      id = R.id.star_count_text;
      TextView starCountText = rootView.findViewById(id);
      if (starCountText == null) {
        break missingId;
      }

      return new RepoItemBinding((LinearLayout) rootView, descriptionText, nameText, starCountText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}