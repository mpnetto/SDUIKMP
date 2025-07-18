# 🏹 Server-Driven UI - Codegen Module

The **Codegen module** generates Jetpack Compose source code from structured **ViewScreens** data. It now relies on a composable template annotated with `@ScreenTemplate` to build the base layout for each generated screen. The template is extracted at generation time so changes to the template automatically propagate to generated code.

### Using a Custom Template
Edit `data/src/commonMain/.../RenderScreen.kt` and adjust the Scaffold or other Compose elements as desired. Ensure the function remains annotated with `@ScreenTemplate`. Running the code generator again will produce new screen files that reflect your changes.
