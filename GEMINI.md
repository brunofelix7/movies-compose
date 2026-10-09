# Project Instructions - Gemini (Antigravity)

This file is the entry point for Gemini in Antigravity. It holds no project-specific values: `CLAUDE.md` is the single source of truth for this project, and every section of it is mandatory for you as well.

> **Reusing in another project**: copy this file unchanged, together with `.agents/` and `.cursor/templates/`. Only `CLAUDE.md` Part A changes per project.

## Before Any Task

1. Read `CLAUDE.md` in full.
   - **Part A** holds the Project Profile, which resolves the placeholders used by the skills (`<basePackage>`, `AppTheme`, `AppFontFamily`, "the design source"), plus the decisions made for this project (layout, legacy code, navigation, UI reference, build tasks).
   - **Part B** is the mandatory workflow: tests in the same task, execution order, previews, git, and build commands.
2. Where `CLAUDE.md` or a skill mentions Cursor paths, use the Antigravity equivalents below.
3. If `CLAUDE.md` and a skill disagree in a way Part A doesn't settle, stop and ask the user instead of picking one.

## Cursor → Antigravity

| In `CLAUDE.md` (Cursor) | In Antigravity |
|---|---|
| `.cursor/rules/<name>.mdc` (always applied) | `.agents/rules/<name>.md` (`trigger: always_on`), which loads `.agents/skills/<name>/SKILL.md` |
| `.cursor/rules/git-commit.mdc`, `.cursor/rules/generate-release-notes.mdc` (on request) | `.agents/skills/git-commit/SKILL.md`, `.agents/skills/generate-release-notes/SKILL.md`: read them when the user asks to commit or for release notes |
| `.cursor/templates/` | The same folder, shared by both tools: templates of the base components cataloged in the `android-base-components` skill |

Each skill has the same content as the Cursor rule with the same name, so the "Rule | Covers" table at the top of `CLAUDE.md` also describes the skills.

## Non-Negotiables

A reminder of `CLAUDE.md` Part B, not a replacement for it:
- Never create or change a class listed in B1 without its test in the same task.
- Work in the B2 order (domain, data, presentation, navigation, verify). Verify with the B5 commands, or with the overrides in Part A when the project defines them.
- Never commit or push unless the user asks; then follow the `git-commit` skill.
