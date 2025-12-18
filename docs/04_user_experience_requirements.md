# 04 — User Experience Requirements

## Layout

The application adapts to the available screen width and presents a simple, consistent layout.

- Top App Bar / Header
  - Title (app name)
  - Compact and medium screens show a smaller title to improve fit
  - Optional platform-specific actions appear in the header when needed

- Navigation
  - Primary sections: Home, Collection, Wish Lists, Depot, Favourites
  - Compact / Medium (phones and narrow windows): Bottom navigation bar only. No side menu or drawer is displayed.
  - Large (wide desktop windows): Persistent left menu (permanent drawer) with navigation entries.

- Content Area
  - Main content is shown to the right of the navigation on large screens or above the navigation bar on compact/medium screens.
  - Content includes lists, details, editors and simple placeholders where features are not yet implemented.

## Responsive behaviour (current app)

The app uses three breakpoints to adapt its UI:

- Compact: narrow screens (small phones)
  - Navigation: bottom navigation bar only
  - Header: reduced title size
  - Content: compact typography and tighter spacings
  - Start screen: Home (shows a temporary "Welcome Home" placeholder)

- Medium: tablets and small desktops
  - Navigation: bottom navigation bar only
  - Header: medium-sized title
  - Content: slightly larger typography than compact

- Large: wide desktops and large windows
  - Navigation: persistent left menu (permanent drawer) and the content area on the right
  - Header: regular title size

## Home behaviour

- Home is the app start destination. On launch the app shows a temporary placeholder that reads: "Welcome Home".
- Home is treated as the app root: navigating to Home clears prior navigation history so Back does not return to earlier screens (this ensures Home acts as the main entry point while the placeholder is in place).

## Typography adjustments

- To keep content readable and well-fitted on small screens the app reduces typography on compact and medium layouts:
  - Compact: smaller title and small navigation labels; primary content uses a compact body style.
  - Medium: slightly larger title than compact but still smaller than large layouts; navigation labels remain compact.
- Large screens use the normal app typography for titles and content.

## Interaction principles

- Keyboard and mouse fully supported on desktop
- Touch-friendly interactions on mobile devices
- Accessibility: icons include content descriptions and text controls use semantic labels
- Responsive UI adapting between narrow and wide screens

## UX notes

- Primary navigation is intentionally minimal while the app is under development: Home, Collection, Wish Lists, Depot and Favourites are immediately accessible.
- The Home placeholder will be replaced with real content in a future release.

## QA / verification notes

(Short, developer-facing checklist useful for manual QA)

- Verify start behaviour: on app launch, the screen shows the Home placeholder "Welcome Home".
- Compact / Medium: there is no drawer or menu; only the bottom navigation bar should be visible.
- Large: a persistent left menu (drawer) must be visible and contain the listed sections.
- Navigation test tags (useful for automated/manual tests):
  - Bottom navigation items: `nav_my_home`, `nav_my_collection`, `nav_my_wishlists`, `nav_my_depot`, `nav_my_favourites`
  - Large menu home item: `menu_home` (menu items for other sections keep their existing tags like `menu_collection`)
- Accessibility: icons should expose their content descriptions via the app's string resources.

## Next steps

- Replace the Home placeholder with the intended home/dashboard content.
- Revisit localization and accessibility labels once placeholder content is added.
