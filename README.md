# VapeV4 — Eaglercraft Port

**Vape V4** has been ported to **Eaglercraft 1.12** by **Zero7Wrath**.

This repository contains the Eaglercraft port of Vape V4, adapted to run within the Eaglercraft environment.

## ClickGUI

The ClickGUI is located at:

`src.game.java.net.minecraft.client.gui.ClickGuiScreen`

## Eaglercraft 1.12

### Java requirements

- **Java 17** is recommended for compiling to TeaVM.
- **Java 8 or newer** is required for the desktop runtime.
- Java must be available through your system `PATH`.

Most Java IDEs can import this repository as a Gradle project.

## Building the web client

1. Run `CompileEPK`.
2. Run `CompileJS` (or the `generateJavaScript` Gradle task in your IDE).
3. Check the `javascript` folder for the generated web client.

## Building an offline download

1. Run `CompileEPK`.
2. Run `CompileJS` (or the `generateJavaScript` Gradle task in your IDE).
3. Run `MakeOfflineDownload`.
4. Check the `javascript` folder.

## Running the desktop client

Open a terminal and run:

```bash
./gradlew runclient
```

Then run/debug the client using the included `eaglercraftDebugRuntime` configuration.

## Credits

Vape V4 was ported to Eaglercraft by **Zero7Wrath**.
