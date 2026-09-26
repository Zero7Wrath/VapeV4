# VapeV4 — Eaglercraft Client Base

**VapeV4** is an **Eaglercraft 1.12 client base for building your own Minecraft utility / hack client**.

The project is intended as a starting point for developers who want to learn how client-side modules, ClickGUI components, keybinds, settings, and Eaglercraft rendering/input systems fit together. It is a base to build on — not a finished client.

> **Use responsibly:** only use client modifications on servers where you have permission to use them and follow the server's rules.

## What you can build

The codebase is structured so you can add your own:

- Combat modules
- Movement modules
- Render modules
- Player utilities
- World utilities
- HUD elements
- ClickGUI components
- Settings and sliders
- Keybinds
- Notifications
- Client-side configuration

## Creating a module

1. Open `com.isacofff.clientbase.modules.features`.
2. Create a new module using `ExampleModule.java` in the `features` folder as a reference.
3. Register the module inside `Manager.java`.
4. Add any settings, keybinds, or GUI components your module needs.

Keep module logic separate from the GUI where possible so the client remains easy to extend.

## ClickGUI

The main ClickGUI screen is located at:

`src.game.java.net.minecraft.client.gui.ClickGuiScreen`

This is the place to work on the client interface, categories, module buttons, settings panels, and other GUI elements.

## Eaglercraft 1.12 build environment

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

## Project structure

A few useful locations:

```text
com.isacofff.clientbase.modules.features/
    Your client modules

com.isacofff.clientbase.modules.Manager
    Module registration

src.game.java.net.minecraft.client.gui.ClickGuiScreen
    ClickGUI

javascript/
    Generated web-client output
```

## Multiplayer server setup

For Eaglercraft multiplayer development, a test server is available here:

https://github.com/catfoolyou/EagsTestServer

For Eaglercraft 1.12 networks, EaglercraftXBungee (EaglerXBungee) can be used with BungeeCord:

https://github.com/lax1dude/eagl3rxbungee

An experimental EaglerXVelocity plugin is also available from the same project.

The proxy software and server setup are separate from this client base.

## Contributing

This project is meant to be built on. If you add a module or client feature, keep the implementation organized and avoid hard-coding functionality directly into unrelated Minecraft classes.

Pull requests and improvements are welcome.

## Credits

This project is based on an Eaglercraft 1.12 source base and adapts it into a client-development project.

Respect the licenses and terms of the upstream projects used by this repository.
