package si.uni_lj.fri.pbd.miniapp1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView textViewPbd = root.findViewById(R.id.text_home_pbd);
        final ImageView imageView=root.findViewById(R.id.image_home);

        imageView.setImageResource(R.mipmap.ul100);
        textView.setText(R.string.text_home);
        textViewPbd.setText(R.string.text_home_pbd);
        return root;
    }
}
