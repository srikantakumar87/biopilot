# Add Compose Preview for StepGoalCard

The goal is to add a `@Preview` Composable function for the `StepGoalCard` in `StepGoalCard.kt`.

## Proposed Changes

### [Component Name]

#### [MODIFY] [StepGoalCard.kt](file:///home/sri/biopilot/android-app/app/src/main/java/io/github/srikantakumar87/biopilot/feature/home/components/StepGoalCard.kt)

- Add necessary imports for `@Preview` and `BiopilotTheme`.
- Add two `@Preview` functions at the bottom of the file:
    - `StepGoalCardPreview`: Shows the card with partial progress.
    - `StepGoalCardAchievedPreview`: Shows the card when the goal is achieved.

## Verification Plan

### Automated Tests
- Run `analyze_file` to ensure there are no syntax errors.
- Run `render_compose_preview` to verify the visual appearance of the new previews.

### Manual Verification
- None required beyond preview rendering.
