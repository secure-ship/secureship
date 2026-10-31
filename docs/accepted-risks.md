# Accepted risks (Grype, fixed branch)

Gate: the pipeline fails on CRITICAL findings (`--fail-on critical`).

Image: `ghcr.io/secure-ship/secureship/notes-api@sha256:06103e5f1c790d6c4f2b4a82e31c28baf85c490763e47c8c8a36b4a6376d6c38`

| Package | CVE | Severity | Status |
|---|---|---|---|
| coreutils | CVE-2016-2781, CVE-2026-56391, CVE-2026-56392 | Medium | No fixed version available at scan time |
| busybox | CVE-2025-60876 | Medium | No fixed version available at scan time |
| gnupg | CVE-2022-3219 | Low | No fixed version available at scan time |

Planned mitigation: distroless runtime image to remove these packages.

Verification: cosign signature checked against workflow ci.yml on refs/heads/fixed (Rekor index 3614329725).