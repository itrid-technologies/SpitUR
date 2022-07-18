package split.com.app.ui.main.view.swap;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import split.com.app.R;
import split.com.app.databinding.FragmentSwapCoinsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.swap_viewmodel.SwapViewModel;


public class SwapCoins extends Fragment {


    FragmentSwapCoinsBinding binding;
    String coins;
    SwapViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSwapCoinsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.swapToolbar.title.setText("Swap");

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applyGradient();

        if (getArguments() != null){
            coins = getArguments().getString("Coins");
            binding.urCoinsValue.setText(coins);
        }


        initClickListeners();
    }

    private void applyGradient() {
        TextPaint paint = binding.inrCoinsValue.getPaint();
        float width = paint.measureText("Tianjin, China");

        Shader textShader = new LinearGradient(0, 0, width, binding.inrCoinsValue.getTextSize(),
                new int[]{
                        Color.parseColor("#D703FF"),
                        Color.parseColor("#0027FE")
                }, null, Shader.TileMode.CLAMP);
        binding.inrCoinsValue.getPaint().setShader(textShader);

//        TextPaint paint1 = binding.urCoinsValue.getPaint();
//        float width1 = paint1.measureText("Tianjin, China");
//
//        Shader textShader1 = new LinearGradient(0, 0, width1, binding.urCoinsValue.getTextSize(),
//                new int[]{
//                        Color.parseColor("#F97C3C"),
//                        Color.parseColor("#FDB54E"),
//                        Color.parseColor("#64B678"),
//                        Color.parseColor("#478AEA"),
//                        Color.parseColor("#8446CC"),
//                }, null, Shader.TileMode.CLAMP);
//        binding.urCoinsValue.getPaint().setShader(textShader1);
    }


    private void initClickListeners() {

        binding.swapToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.swapButton.setOnClickListener(view -> {
            String swapValue = binding.inrCoinsValue.getText().toString();
            if (!swapValue.isEmpty() && !swapValue.equalsIgnoreCase("0")){
              viewModel = new SwapViewModel(swapValue);
              viewModel.init();
              viewModel.getCoins_data().observe(getViewLifecycleOwner(), basicModel -> {
                  if (basicModel.isStatus()){
                      Navigation.findNavController(view).navigate(R.id.action_swapCoins_to_successfullySwapped);
                  }
              });
            }
        });

        binding.tvMax.setOnClickListener(view -> {
            String availableCoin = binding.urCoinsValue.getText().toString().trim();
            binding.inrCoinsValue.setText(availableCoin);
        });
    }
}