# BetterGamma

Fullbright for Minecraft that leaves your video settings alone.

---

[![Discord Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/social/discord-singular_64h.png)](https://s.deftu.dev/discord)
[![Ko-Fi Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/donate/kofi-singular_64h.png)](https://s.deftu.dev/kofi)
[![Modrinth Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/available/modrinth_64h.png)](https://modrinth.com/mod/bettergamma)
[![CurseForge Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/available/curseforge_64h.png)](https://www.curseforge.com/minecraft/mc-mods/bettergamma)

---

## What it does
Many fullbright mods crank your brightness setting past its limit. That value gets saved to `options.txt`, so turning the mod off or removing it can leave your game blinding, and other clients that share the file get it too.

BetterGamma never changes the setting. It only changes the brightness the game uses while it lights the world, so your own slider stays exactly where you left it.

Fullbright also gives you a hidden night vision effect that only your game sees. Shader packs that react to night vision light up with it, which plain brightness tweaks can't do. Servers can't clear it, and it never shows up in your effects.

Turning fullbright on or off fades smoothly instead of snapping, and shader packs that react to night vision fade with it.

---

## How do I use it?
BetterGamma needs [OneConfig][oneconfig]. Press G to turn fullbright on or off, or change the key in the OneConfig menu along with how bright it gets.

You can also run `/bettergamma toggle`, or `/bettergamma brightness <0-1500>` to pick a brightness and turn it on. 100% matches the game's Bright setting.

---

## What versions does it support?
Minecraft 1.21 to 1.21.1, 1.21.4, 1.21.5, 1.21.8, 1.21.10, 1.21.11 and 26.1 to 26.3 on Fabric, and 1.8.9 on Ornithe.

---

[![BisectHosting](https://www.bisecthosting.com/partners/custom-banners/8fb6621b-811a-473b-9087-c8c42b50e74c.png)](https://s.deftu.dev/bisect)

---

**This project is licensed under [AGPL-3.0-or-later][agpl]**\
**&copy; 2023 Deftu**

[oneconfig]: https://modrinth.com/mod/oneconfig
[agpl]: https://www.gnu.org/licenses/agpl-3.0.en.html
