package com.example.newspotifyclone.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newspotifyclone.R;
import com.example.newspotifyclone.databinding.FragmentSearch0Binding;
import com.example.newspotifyclone.databinding.FragmentSearchBinding;
import com.example.newspotifyclone.helper.SearchItemAdapter;
import com.example.newspotifyclone.model.SearchItemModel;

import java.util.ArrayList;

public class SearchFragment0 extends Fragment implements SearchItemAdapter.CategoryInterface {
    FragmentSearch0Binding fragmentSearch0Binding;
    public interface OnSearchClickListener{
        void onSearchClickListener();
    }
    RecyclerView searchItemGridRecyclerView;
    ArrayList<SearchItemModel> searchItemModelArrayList;
    SearchItemAdapter searchItemAdapter;
    View customSearchBar;
    private OnSearchClickListener searchClickListener;
    public SearchFragment0() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnSearchClickListener){
            searchClickListener = (OnSearchClickListener) context;
        }else {
            throw new RuntimeException(context.toString()+
                    " must implement onSearchClickListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearch0Binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search0,container,false);
        View view=fragmentSearch0Binding.getRoot();
        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search0, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customSearchBar = fragmentSearch0Binding.myCustomSearchbar;
        searchItemGridRecyclerView = fragmentSearch0Binding.searchItemGridRecycler;

        searchItemModelArrayList = new ArrayList<>();

        addGridItems();

        searchItemAdapter = new SearchItemAdapter(searchItemModelArrayList,getContext(),this::onCategoryClick);
        searchItemGridRecyclerView.setAdapter(searchItemAdapter);
        searchItemAdapter.notifyDataSetChanged();

        customSearchBar.findViewById(R.id.search_bar_image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClickListener.onSearchClickListener();
            }
        });
        customSearchBar.findViewById(R.id.search_bar_text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClickListener.onSearchClickListener();
            }
        });

    }
    public void addGridItems(){
        SearchItemModel itemModel1 = new SearchItemModel("PodCasts","https://s3-alpha-sig.figma.com/img/938e/03c8/73a88ec2baf336e2bdfd8aa0db01519b?Expires=1691971200&Signature=QLZ-6OVApe4u5ClBxyYHVa5rCyTBIavkKZde58uLUrepzn87Vh0tgw4tEfmvXDSZ7G2qYS5CKX-sakJs53g6WseU9-7gayfq6YYtjAo5S9xTd3-mWq3bWzPeXOmYQfQaUPyDzy7mkKGgbFbXNk8YrdT3K3nuKeuzpDo~b8qqd4vkTEcEbIN1XtL0NOO6UCL7Y1t3EfFnLBI8kfrg4b1N8mu4RGBBRe15GfuNGHxc8g5ftNG4cXVrf0cIpM73z5wsSwOCglbFFgAzBzAj9-C5GMnp9Zj-xUFOpZdczQE8hR6MU-lRLt6u29f8fXGTD0GJnHE0rW-7kwTn~4wpgMX7hQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel2 = new SearchItemModel("New Releases","https://s3-alpha-sig.figma.com/img/f7d8/a264/dae751d23d92f17e7532001c2e36985d?Expires=1691971200&Signature=Bj~dCSLqAXmJ1RhQv4ixE4p8BQIMbTvH64I17EN3BmpA-KGBHCS5xe5Tbp36LJ6oZ6D8jOVMYldtx4y4eCWqeb12UixuvWsXOyGAOf1LCs1Jrq246mHvZWJJofzWR7Qx9be9OKwIQ4-5m37UnfZLaQuSoucmaf1~jqi8TWmpJfrHPyGgPFE6gz6oD3bR5m7GmLlQ290ApoyrkYsqZ2DgWgiaS7DHnpXo7VTmaKA~nfCfJ1gxkpsjkzX1EGWbTFaZShoXNWDncHELgqjCbUC7QswWc14-i-xkg7NQWZkkBhQ4fYAFz0TYIBad2NfNsmyV1FOM7-4UlzbcJzIH6N~Haw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel3 = new SearchItemModel("Charts","https://s3-alpha-sig.figma.com/img/3ec0/b23a/6c4a2ad0ee6c5578836018f141409182?Expires=1691971200&Signature=N59tXhsMGbe~eEPNuZtY4LC~yf6aLMqXMbY-q8FWg-~EgmytbXY2YiG5G7UbQB7tv5mJXiz-GkuiUHNiu1TGqUnFSGjTtVWArF4SmgYKFQqgvcQ3ZdZn-dtG~mUOt~dtzi18jnKPcpUES7DVEprrwdkMyNwFJMoHbspmmM~HQcJ8AicP27yBc77njKkGZTFjBmYhjEAgF5wqvNhBz6PPbmDMzdiEw8nzD6~iNW~duHJXQ6cMFgn9M1Ia22bwjz9CWoe0-rgV95Hus08vPYhwxXpQZCWvhH3yzcDFnHqHJLK1eIzh7ja-xyaw3agOIxxhsFVmabQSCIcCdme0HET75w__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel4 = new SearchItemModel("Concerts","https://s3-alpha-sig.figma.com/img/b551/9264/3c64ecb9424fb81eb898a45b099bd3cb?Expires=1691971200&Signature=hCdJdTx8dyYrlvmGkms9wmVKu42CsEgkMen4x5L15UgegVy9QqG2JQ5V8drAarkCN9QbcsGIH4Y7HlEKEjHOVdiRic3OBYKt~0S~CDxBFw6liiLGURaebB435OE94-bFqBGh~HIkCrImLKgpYIA3XlZ~PWCOPTpdDkhP-dbIksk07MdyxlDhkHnBuMsbHr0YCAxI1AggeBxJyItDib9Z57eSJqHiSgJVG6Aomp7GBwj7DCmmrXP0t9c5IEiP50C4YDfSxYL89fjhBo0CT9T7duuwql55MUQfSvQRCL~3DlGGL4XsWsXZccryR9jJk325pI5Y1CCf2Fy1BvyASXJN3g__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel5 = new SearchItemModel("Made for you","https://s3-alpha-sig.figma.com/img/da81/9bca/4756f3f88b30cee7d0e9d546f6447ca5?Expires=1691971200&Signature=hasP-Sjb8kg5tItosLp692NuWU2RFnZAOZeBl1e~R7U4-2rYKpbT3U5NlbxoriHt368C00nVIsi6Nnq88sgceHZJrIRShFB2NimLIWghklc~ExH4qbJyvkZd~4WCKxLy0xUFc-5U5cmrYkz8hYVBg8AobqFUUb5qqNOHfCgPCxgcC7~TI-BCAoF06cTvZQUC5cS83tagdbw6Qp5Dwtox2yIA0cfhSTnY7gcXx9Pm-Kb4JOWzRb7hGLwoS6qxF1fglEyLj120tHufICfrqVF1daTPQv2VoKXjXxY5hxKfCVDROcifMRR8IZPj20TVBaAwo43Sua-uG~8CvAR9MGPy4A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel6 = new SearchItemModel("At home","https://s3-alpha-sig.figma.com/img/86ff/4247/23f989fec8b7d6279ce1bfb87131ba99?Expires=1691971200&Signature=G3aHe8Hu2RY62mt2foS~FDJHZnZTtpAA6PGec4x9bLzTPvedyoF~4Cv5mZj2~FnhXFOHx7-39iHNavZUqj155Hlv5GfweoL-COe3~6RVr1Rhsuj1bIwtJeytQRrazpO9AxZxHIwhssiHcRLHIkQ5IDZuA8tPFW~novptB13TP~y9Hyi4gIHdzzUE4Gl7JEB6oBiO1ujYqnci5S5xFVQld7ya9a5IRy05dats9KJ2HrzPjtGmdPz1skpCMOOneqehisfxRBIaZnTpl0rZ48E54sEDe6F40lws7naOqxTcuzzHcXjq4qbzfX8iX~x14QK20USwGrRBCM5dQHWE4Mc~Tg__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel7 = new SearchItemModel("Love songs","https://s3-alpha-sig.figma.com/img/03ab/175d/ac952be1c5b6b0833ec341e4a845f067?Expires=1691971200&Signature=OpSrQN8BI7q0Q1V8SWcuJZUqO2rfzsPrQ2VU2YgNSZ6cOS8BXTszzeHuk~W~sZgXr3HxvkcBomygtfRC7hg~wxR0MyX5HqWEx6FCTo62cQACM8-Qo1dCvDU-GnqTkB26FROQyTwF6riBo5JRFR6CBzDsBTsP60Mq71OR7Lmg5c2O5R~GzbZyEk3gRjEDs6xy1YjszOxOCE77i-AEuerJtORfeuNwKyjoxGXQyFCw9JAnGFTlMI-Z36pNzInTfMhnYe3QaLGflKq-oXhT-brskPbQlq9yB53BNQ4txisyHHAAjc~gKgcMOvgDo7Rhinn19AakY~Q803KN~zOvyfkgEw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel8 = new SearchItemModel("Covid-19 Guid","https://s3-alpha-sig.figma.com/img/12a0/97be/391e7888ab6f991644d1366f5476f101?Expires=1691971200&Signature=aLVGAgtoGKMPxdfyGAQqv2kEFunDrjC97h9BN-d-VizTGu8oODQUGeBbP8r7t3Un4anyUzTng3EKp-KHMQc1FZwVVGWQ6~KkRPk7n6RuArt4fdshlMl~fEsv3AUz8qnj3dYHnFf2H7~2KItHXqCVmw7MvSJKMKkRirnMmbMjiXp4dbI1ju6Qf3R2Cqs9cozeF~gSC6UIMwfOcP7bbqPw3QZlF-k~eNVRhDCc1JwfgHX5AAmAV4fWou844flCp0wX5NVAzAUXKLrAXEAnIxWzh2NF0Ry4XQnxys3pVizdZlBuqzzOkA1eHjYPqbSheN0mxRS-EH0XznbIyqxMF1Dnjw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel9 = new SearchItemModel("Only you","https://s3-alpha-sig.figma.com/img/03ab/175d/ac952be1c5b6b0833ec341e4a845f067?Expires=1691971200&Signature=OpSrQN8BI7q0Q1V8SWcuJZUqO2rfzsPrQ2VU2YgNSZ6cOS8BXTszzeHuk~W~sZgXr3HxvkcBomygtfRC7hg~wxR0MyX5HqWEx6FCTo62cQACM8-Qo1dCvDU-GnqTkB26FROQyTwF6riBo5JRFR6CBzDsBTsP60Mq71OR7Lmg5c2O5R~GzbZyEk3gRjEDs6xy1YjszOxOCE77i-AEuerJtORfeuNwKyjoxGXQyFCw9JAnGFTlMI-Z36pNzInTfMhnYe3QaLGflKq-oXhT-brskPbQlq9yB53BNQ4txisyHHAAjc~gKgcMOvgDo7Rhinn19AakY~Q803KN~zOvyfkgEw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");
        SearchItemModel itemModel10 = new SearchItemModel("Rock Music","https://s3-alpha-sig.figma.com/img/2d8d/aab7/19a16b98ea7a0d9abfa2ac6ad3ad671b?Expires=1691971200&Signature=RVlDEzhxzFD3ZWwmhUln4oOxavr0OKXGE-Tngtr2beXiISITfe~YjYTHhW8CYzXpc-RGwbr9qcSKNlKggIQQ7cG0n24xcOp2FBi3v8yNuJG6htNWv4j63UicDwalrBjjww-mvaVUYx6Z~7n568CLpFjInpXJbav8ShpQscOdiS5FoDyEB~FYqxTyHeEEzody6rda3kfX4M459b3M1q02JoAr9GWI3K83MI3T333snZLE63tfCo12KpAgUv28FF~MoubqYPum77eVkx7goV946yjfSJ92~y9Lr8UKHhtvfqbi6x5U9GjR0GD63m~5LeFMKIpsPZdLcebHhb7w3-AJWA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4");

        searchItemModelArrayList.add(itemModel1);
        searchItemModelArrayList.add(itemModel2);
        searchItemModelArrayList.add(itemModel3);
        searchItemModelArrayList.add(itemModel4);
        searchItemModelArrayList.add(itemModel5);
        searchItemModelArrayList.add(itemModel6);
        searchItemModelArrayList.add(itemModel7);
        searchItemModelArrayList.add(itemModel8);
        searchItemModelArrayList.add(itemModel9);
        searchItemModelArrayList.add(itemModel10);

    }

    @Override
    public void onCategoryClick(int position, ArrayList<SearchItemModel> searchGridItems) {

    }
}