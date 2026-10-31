# Accepted risks (Grype, fixed branch)

Gate: the pipeline fails on CRITICAL findings (`--fail-on critical`).

Image: `ghcr.io/secure-ship/secureship/notes-api@sha256:4c52c77e3ca7652f0e3a59bf4698ca519734fa30170073d5d34f41d2e6931a1a`

| Package | CVE | Severity | Status |
|---|---|---|---|
| coreutils | CVE-2016-2781, CVE-2026-56391, CVE-2026-56392 | Medium | No fixed version available at scan time |
| busybox | CVE-2025-60876 | Medium | No fixed version available at scan time |
| gnupg | CVE-2022-3219 | Low | No fixed version available at scan time |

Planned mitigation: distroless runtime image to remove these packages.

Verification: cosign signature checked against workflow secureship-pipeline.yml on refs/heads/fixed (Rekor index 3614436139).