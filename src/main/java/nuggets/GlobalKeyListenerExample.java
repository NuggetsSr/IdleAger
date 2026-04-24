package nuggets;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListenerExample implements NativeKeyListener {

    IdleAger him = new IdleAger();
    @Override
	public void nativeKeyReleased(NativeKeyEvent e) {
        // call method to update idle time
		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        him.updateKeystroke();
	}

	public static void init() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
        System.out.println("Hello World");
	}
}
