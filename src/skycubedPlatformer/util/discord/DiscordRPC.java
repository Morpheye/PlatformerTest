package skycubedPlatformer.util.discord;

import java.io.File;
import java.time.Instant;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.activity.Activity;

public class DiscordRPC {
	public static final long CLIENT_ID = 1107285104218361937L;
    public static Core core = null;
    public static boolean LIBRARY_LOADED = false;

    public static boolean discordRPCEnabled = true;
    public static Instant initTime;

    public static void init() {
        new Thread(DiscordRPC::_init).start();
    }

    private static void _init() {
        File discordLibrary = DiscordNativeLibrary.getNativeLibrary();
        if (discordLibrary == null) {
        	System.out.println("Discord library not found.");
            return;
        }
        // Initialize the Core
        Core.init(discordLibrary);
        System.out.println("DiscordRPC Core initialized.");
        LIBRARY_LOADED = true;

        onEnabledStatusChanged();

        startCallbackThread();
        System.out.println("Started DiscordRPC callback thread");
        initTime = Instant.now();
    }

    public static void onEnabledStatusChanged() {
        if (!LIBRARY_LOADED) {
        	System.out.println("DiscordRPC library not loaded correctly, unable to update rich presence");
            return;
        }

        if (discordRPCEnabled) enableRPC();
        else disableRPC();
    }

    private static void startCallbackThread() {
        Thread t = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (core != null && core.isOpen()) core.runCallbacks();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    disableRPC();
                    break;
                }
            }
            disableRPC();
        }, "Discord_RPC_Callback_Handler");
        t.start();
    }

    private static void enableRPC() {
        if (core != null) {
        	System.out.println("DiscordRPC already enabled, restarting... ");
            disableRPC();
        }
        new Thread(() -> {
            createCore();
            updateStatus("Just started", "");
            System.out.println("DiscordRPC started");
        }).start();
    }

    private static void disableRPC() {
        if (core != null)
            core.close();
        core = null;
        System.out.println("DiscordRPC disabled in options, turn on to activate");
    }

    private static void createCore() {
        try (CreateParams params = new CreateParams()) {
            params.setClientID(CLIENT_ID);
            params.setFlags(CreateParams.getDefaultFlags());

            try {
                core = new Core(params);
            } catch (GameSDKException e) {
            	System.out.println("DiscordRPC Core creation failed: ");
                e.printStackTrace();
            }
        }
    }

    public static void updateStatus(String text1, String text2) {
        String largeText = text1;
        String smallText = text2;

        new Thread(() -> updateActivity(largeText, smallText)).start();
    }

    private static void updateActivity(String details, String state) {
        if (!LIBRARY_LOADED) return;
        if (!discordRPCEnabled) return;

        try (Activity activity = new Activity()) {
            activity.setDetails(details);
            if (state != null)
                activity.setState(state);

            activity.timestamps().setStart(initTime);

            activity.assets().setLargeImage("icon");
            activity.assets().setLargeText("SkyCubed Platformer");
            if (core != null && core.isOpen())
                core.activityManager().updateActivity(activity);
        }
    }
}
