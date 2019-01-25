import com.sun.jna.*;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.platform.win32.WinDef.RECT;

import java.awt.*;
import java.util.*;

import static java.lang.System.out;

enum GameWindowType {GAME, REPLAY, OBSERVE};

public class GameWindows {
    private final static User32 USER32 = User32.INSTANCE;
    private static Map<String, Pointer> nativeHandleByDescriptor = new HashMap<String, Pointer>();

    private interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }

        boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer userData);
        boolean EnumChildWindows(Pointer hWnd, WNDENUMPROC lpEnumFunc, Pointer userData);
        boolean GetWindowRect(Pointer hWnd, RECT bounds);
        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);
    }



    private static class WindowEnumProc implements User32.WNDENUMPROC {
        protected Pointer windowHandle;
        protected String title;
        protected String tableId;
        @Override
        public boolean callback(Pointer windowHandle, Pointer arg) {
            this.windowHandle = windowHandle;
            this.title = getWindowTitle();
            //System.out.println("detect table " + title);
            if (isGameWindow()) {
                this.tableId = getTableId();
                System.out.println(title);
                System.out.println(tableId + " found");
                registerGameWindow();
            }
            return true;
        }

        private void registerGameWindow() {
            if (!Tables.isTableDetected(tableId)) {
                GameTable gameTable = new GameTable(tableId, title);
                gameTable.setTableType(GameWindowType.GAME);
                Tables.addTable(gameTable);
                nativeHandleByDescriptor.put(tableId, windowHandle); //TODO deleted
            }
        }

        private boolean isGameWindow() {
            return !title.isEmpty() && title.contains("Limit") && title.contains("Logged");
        }

        protected String getTableId() {
            return windowHandle.toString().substring(9).toUpperCase();
        }

        protected String getWindowTitle() {
            byte[] windowText = new byte[512];
            USER32.GetWindowTextA(windowHandle, windowText, 512);
            return Native.toString(windowText).trim();
        }
    }

    private static class WindowEnumChildProc extends WindowEnumProc {
        @Override
        public boolean callback(Pointer windowHandle, Pointer arg) {
            this.windowHandle = windowHandle;
            String title = getWindowTitle();
            out.println(title);
            String hexDescriptor = getTableId();
            out.println(hexDescriptor);
            return true;
        }
    }

    public static void detect() {
        USER32.EnumWindows(new WindowEnumProc(), null);
        System.out.println("detect method finished");
        //USER32.EnumChildWindows(tableHandle, new WindowEnumChildProc(), null);
    }

    public static Rectangle getRectangle(String tableId) {
        Pointer windowHandle = nativeHandleByDescriptor.get(tableId);
        RECT windowBounds = new RECT();
        USER32.GetWindowRect(windowHandle, windowBounds);
        return windowBounds.toRectangle();
    }
}