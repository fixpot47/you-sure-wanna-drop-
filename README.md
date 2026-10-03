# You Sure Wanna Drop?

A tiny client-side Fabric mod for Minecraft 26.3 that asks for confirmation before the player drops the currently selected hotbar item.

## Behavior

- Uses Minecraft's configured **Drop Selected Item** keybind, so it works even when the player changes the key from `Q`.
- First drop attempt for an item opens a confirmation screen.
- **Drop** approves the current item and performs the original drop action.
- The same item can then be dropped repeatedly without another warning while that hotbar slot stays selected.
- Switching to a different hotbar slot clears the approval. Returning to the old slot requires confirmation again.
- If a different item appears in the approved slot, it requires confirmation as well.
- `Ctrl + Drop` keeps vanilla's "drop whole stack" behavior after confirmation.
- English and Russian are included.
- No config screen and no Mod Menu dependency.

## Build

Requires Java 25 and Gradle 9.7.1.

```bash
gradle build
```

The built JAR will be in `build/libs/`.
