package cn.hanye.fcitx5enhancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import cn.xiaym.fcitx5.Fcitx5;

public final class Fcitx5enhancer {
    public static final String MOD_ID = "fcitx5enhancer";
    private static final Logger LOGGER = LoggerFactory.getLogger("assets/fcitx5-enhancer");

    public static void init(Path gameDir) {
        // Write common init code here.
        Path dataDir = gameDir.resolve(".fcitx5-enhancer");

        if (Files.notExists(dataDir)) {
            try {
                Files.createDirectory(dataDir);
            } catch (IOException ex) {
                LOGGER.error("Failed to create data directory!");
                throw new RuntimeException(ex);
            }
        }

        Path nativeLib = dataDir.resolve("native.so");
        try {
            if (Files.exists(nativeLib)) {
                LOGGER.info("Found .fcitx5-enhancer/native.so, loading...");
            } else {
                LOGGER.info("Extracting and loading built-in native library...");
                nativeLib = dataDir.resolve(".tmp_internal.so");

                try (InputStream resourceAsStream = Fcitx5enhancer.class.getClassLoader().getResourceAsStream("native/libfcitx5_detector.so")) {
                    assert resourceAsStream != null;
                    Files.write(nativeLib, resourceAsStream.readAllBytes(), StandardOpenOption.CREATE);
                }
            }

            System.load(nativeLib.toAbsolutePath().toString());
            LOGGER.info("Successfully loaded the native library.");
        } catch (Exception ex) {
            LOGGER.error("Failed to load native library for Fcitx5-Enhancer!");
            LOGGER.error("-> This library is compiled for Linux x86_64 (glibc), if your platform is different,");
            LOGGER.error("   please compile the native library yourself and place it at:");
            LOGGER.error("    .minecraft/.fcitx5-enhancer/native.so");

            throw new RuntimeException(ex);
        }

        if (!Fcitx5.findWindow()) {
            LOGGER.warn("Fcitx5 is not running, this mod will not take effect unless it's started.");
        }
    }
}
