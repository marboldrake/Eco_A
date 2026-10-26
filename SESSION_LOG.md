# Session Log — Ecoa

## 2026-10-23/24
- Read Metrolist frontend architecture (Compose, Material 3).
- Fixed GitHub Actions: replaced `blacksmith-4vcpu-ubuntu-2404` runners with `ubuntu-latest` in build.yml, build_pr.yml, build_quick.yml, release.yml; raised timeouts.
- Explained token/context limits, free-tier behavior, model choices (Gemini 2.5 Pro recommended for UI).
- User configured Gemini AI Studio API key (free tier, no billing).

## 2026-10-25
- Explained context limits, token resets, free tier behavior (no daily cap; rate limits only).
- Created PR via GitHub web → build_pr.yml compiled successfully (GMS debug APK).
- Installed APK on device (Android "dangerous app" warning is normal for debug builds).
- **Rebrand Metrolist → Ecoa (UI-only):**
  - build.gradle.kts: app name defaults "Metrolist" → "Ecoa"/"Ecoa Debug"
  - workflows: "Metrolist Nightly" → "Ecoa Nightly"
  - styles.xml + values-v31: Theme.Metrolist → Theme.Ecoa, Theme.Widget.Metrolist → Theme.Widget.Ecoa, MetrolistPlaylistWidget* → EcoaPlaylistWidget*
  - AndroidManifest.xml + 8 layouts: theme/style references updated
  - Icon vectors (foreground, v31, monochrome): "M" shape → "E" shape
  - metrolist_strings.xml (English): 12 user-facing strings updated
  - Kotlin: User-Agent, X-Title, Discord label, crash report, playback error, wrapped playlist name → "Ecoa"
  - app_name.xml: "Metrolist" → "Ecoa"
- **Fixed build failure:** missed `Theme.Widget.Metrolist` → `Theme.Widget.Ecoa` rename in 3 additional layouts (widget_music_player, widget_compact_wide, widget_compact_square).
- **Icon integration:** User provided PNG (icono_sin_tetxo). Copied to all mipmap densities (ic_launcher.png, ic_launcher_round.png, ic_launcher_static.png), drawable foreground, tv_banner. Removed old .webp icons.
- **About screen update:**
  - Lead developer → marboldrake with uploaded avatar (marboldrake_avatar.jpg)
  - ContributorAvatar avatarUrl type: String → Any (supports drawable resource IDs)
  - Mo Agamy moved to collaborators (credits preserved)
  - Community links: "View repository" → marboldrake/Eco_A; added "View original repository (Metrolist)" → MetrolistGroup/Metrolist
  - DeveloperSocials → marboldrake's GitHub only
  - App header: "METROLIST" → "ECOA"
- Created AGENTS.md (repo + workspace root) with project goals, rules, build commands, gotchas.

## Pending / not done
- Static PNG icons for Android < 8.0 still show old "M" in some densities (replaced but verify).
- Monochrome icon still uses "E" vector (user's icon is multi-color; may want a B&W version).
- Updater.kt APK filenames ("Metrolist.apk") left intact (tied to CI artifact names).
- PlaylistExporter.kt directory name ("MetrolistExports") left intact (on-disk functional).
- Copyright headers left as "Metrolist Project (C) 2026".
- Other language files NOT edited (AGENTS.md rule: only default English).
- github_releases_url still points to upstream Metrolist (intentional for updates).

## Key gotchas discovered
- `Theme.Widget.Metrolist` is NOT a substring of `Theme.Metrolist` — separate find/replace needed.
- `app_name.xml` in `values/` hardcodes the app name — must be kept in sync with build.gradle.kts resValue.
- `ContributorAvatar` AsyncImage model accepts `Any` (String URL or drawable Int).
- Build uses `assembleGmsDebug` in CI (PR workflow), not foss.
## 2026-10-26
- Defined Nocturne Audio base palette (monochromatic AMOLED) in Theme.kt.
- Defined base dimensions/radii/spacing in Dimensions.kt (media 8dp, cards 12dp, pills 24dp; 4dp grid).
- Changed DefaultThemeColor to white and ThumbnailCornerRadius 3dp -> 8dp.
