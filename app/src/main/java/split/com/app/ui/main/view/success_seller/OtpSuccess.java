package split.com.app.ui.main.view.success_seller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import split.com.app.R;
import split.com.app.databinding.FragmentOtpSuccessBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;


public class OtpSuccess extends Fragment {


    FragmentOtpSuccessBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtpSuccessBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Glide.with(Split.getAppContext()).load(R.drawable.success_gif).into(binding.successGif);

//        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                ActivityUtil.gotoHome(Split.getAppContext());
//            }
//        });

        clickListeners();
    }

    private void clickListeners() {

        binding.btnHome.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.home2);
        });

        binding.copyLink01.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper",binding.appLink.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();
        });

        binding.whatsapp.setOnClickListener(view -> {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=split.com.app");
            try {
                requireActivity().startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "WhatsApp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.FB.setOnClickListener(view -> {
            shareAppWithSocial(requireContext(),Constants.FACEBOOK_PACKAGE_NAME,"","");
        });

        binding.twitter.setOnClickListener(view -> {
            shareAppWithSocial(requireContext(),Constants.TWITTER_PACKAGE_NAME,"","");
        });

        binding.insta.setOnClickListener(view -> {
            shareAppWithSocial(requireContext(),Constants.INSTAGRAM_PACKAGE_NAME,"","");

        });

        binding.discord.setOnClickListener(view -> {
            shareAppWithSocial(requireContext(),Constants.DISCORD_PACKAGE_NAME,"","");

        });

    }


    public static void shareAppWithSocial(Context context, String application, String title,
                                          String description) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setPackage(application);

        intent.putExtra(android.content.Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, description);
        intent.setType("text/plain");

        try {
            // Start the specific social application
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            // The application does not exist
            Toast.makeText(context, "app have not been installed.", Toast.LENGTH_SHORT).show();
        }


    }
}