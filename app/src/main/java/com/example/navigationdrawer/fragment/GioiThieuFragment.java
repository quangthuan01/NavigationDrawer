package com.example.navigationdrawer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.GioiThieuAdapter;
import com.example.navigationdrawer.model.GioiThieu;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class GioiThieuFragment extends Fragment {
    private ViewPager2 locationViewPager2;
    private List<GioiThieu> gioiThieus;
    private GioiThieu gioiThieuEiffelTower;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_gioi_thieu, container, false);

        locationViewPager2 = view.findViewById(R.id.locationsViewPager2);
        gioiThieus = new ArrayList<>();
        gioiThieuEiffelTower = new GioiThieu();
        gioiThieuEiffelTower.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/anhmain.jpg?alt=media&token=3e5a7b22-0c53-42d0-891e-2895d89a385d";
        gioiThieuEiffelTower.title = "Nguyen Quang Thuan";
        gioiThieuEiffelTower.location = "FaceBook App Writer";
        gioiThieuEiffelTower.starRating = 4.8f;
        gioiThieus.add(gioiThieuEiffelTower);

        GioiThieu gioiThieuMountainView = new GioiThieu();
        gioiThieuMountainView.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/sign_in.PNG?alt=media&token=c12287e5-094b-4421-b795-836e21e5dd18";
        gioiThieuMountainView.title = "Sign In";
        gioiThieuMountainView.location = "Mobie Money";
        gioiThieuMountainView.starRating = 4.5f;
        gioiThieus.add(gioiThieuMountainView);

        GioiThieu gioiThieuTajMahal = new GioiThieu();
        gioiThieuTajMahal.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/sign_up.PNG?alt=media&token=bea28ac6-8aae-4671-a9a1-b8030424388d";
        gioiThieuTajMahal.title = "Sign Up";
        gioiThieuTajMahal.location = "Mobie Money";
        gioiThieuTajMahal.starRating = 4.3f;
        gioiThieus.add(gioiThieuTajMahal);

        GioiThieu gioiThieuHCM = new GioiThieu();
        gioiThieuHCM.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/khoanloaichi.PNG?alt=media&token=3d35ea21-6fb0-4e07-8f9f-269783a46b50";
        gioiThieuHCM.title = "Revenues";
        gioiThieuHCM.location = "Mobie Money";
        gioiThieuHCM.starRating = 4.2f;
        gioiThieus.add(gioiThieuHCM);


        GioiThieu gioiThieu1 = new GioiThieu();
        gioiThieu1.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/add_loaichi.PNG?alt=media&token=ff7cd83c-02e0-4d1d-aaa7-e7e46f9de9fb";
        gioiThieu1.title = "Spending Money";
        gioiThieu1.location = "Mobie Money";
        gioiThieu1.starRating = 4.1f;
        gioiThieus.add(gioiThieu1);

        GioiThieu gioiThieu2 = new GioiThieu();
        gioiThieu2.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/add_khoanchi.PNG?alt=media&token=c2af689e-956a-43da-b7d4-cfde17c08562";
        gioiThieu2.title = "Expenditures";
        gioiThieu2.location = "Mobie Money";
        gioiThieu2.starRating = 4.0f;
        gioiThieus.add(gioiThieu2);

        GioiThieu gioiThieu3 = new GioiThieu();
        gioiThieu3.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/add_loaithu.PNG?alt=media&token=6772c00e-6ea3-4d80-826f-86b169ae42e1";
        gioiThieu3.title = "Spending Money";
        gioiThieu3.location = "Mobie Money";
        gioiThieu3.starRating = 3.9f;
        gioiThieus.add(gioiThieu3);

        GioiThieu gioiThieu4 = new GioiThieu();
        gioiThieu4.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/add_khoanthu.PNG?alt=media&token=4ba184a4-1cd5-4275-a2d8-b443dc1ad0d1";
        gioiThieu4.title = "Extra Revenues";
        gioiThieu4.location = "Mobie Money";
        gioiThieu4.starRating = 3.8f;
        gioiThieus.add(gioiThieu4);

        GioiThieu gioiThieu5 = new GioiThieu();
        gioiThieu5.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/gioithieu.PNG?alt=media&token=0875cdac-f9ee-4625-b8d1-908fdf9ae7a5";
        gioiThieu5.title = "Extra Revenues";
        gioiThieu5.location = "Mobie Money";
        gioiThieu5.starRating = 3.7f;
        gioiThieus.add(gioiThieu5);

        GioiThieu gioiThieu6 = new GioiThieu();
        gioiThieu6.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/gioithieu.PNG?alt=media&token=0875cdac-f9ee-4625-b8d1-908fdf9ae7a5";
        gioiThieu6.title = "Introduce";
        gioiThieu6.location = "Mobie Money";
        gioiThieu6.starRating = 3.6f;
        gioiThieus.add(gioiThieu6);

        GioiThieu gioiThieu7 = new GioiThieu();
        gioiThieu7.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/thongke.PNG?alt=media&token=fe40bfab-ec06-4a53-80d8-c7de389cc819";
        gioiThieu7.title = "Statiscal";
        gioiThieu7.location = "Mobie Money";
        gioiThieu7.starRating = 3.5f;
        gioiThieus.add(gioiThieu7);

        GioiThieu gioiThieu8 = new GioiThieu();
        gioiThieu8.imageUrl = "https://firebasestorage.googleapis.com/v0/b/navigationdrawer-d9ebc.appspot.com/o/taikhoan.PNG?alt=media&token=ee063114-211c-44a4-9864-9fc0683b87d7";
        gioiThieu8.title = "Account";
        gioiThieu8.location = "Mobie Money";
        gioiThieu8.starRating = 3.4f;
        gioiThieus.add(gioiThieu8);

        locationViewPager2.setAdapter(new GioiThieuAdapter(gioiThieus));
        locationViewPager2.setClipToPadding(false);
        locationViewPager2.setClipChildren(false);
        locationViewPager2.setOffscreenPageLimit(3);
        locationViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer  = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull  View page, float position) {
                    float r  = 1-Math.abs(position);
                    page.setScaleY(0.95f + r * 0.05f);


            }
        });
        locationViewPager2.setPageTransformer(compositePageTransformer);
        return view;
    }
}