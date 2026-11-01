# secureship

A reusable DevSecOps pipeline for GitHub Actions. Every push is scanned for secrets, code flaws, vulnerable dependencies and Dockerfile problems. If the gates pass, the image is built, scanned, pushed by commit SHA, given an SBOM, signed keylessly with Cosign and verified.

## Use it in 5 minutes

1. Copy `docs/example-caller.yml` into your repository as `.github/workflows/secureship.yml`.
2. Set `app-path` and `image-name` for your app.
3. (Optional) Add a `secureship.yml` at your repo root, see `secureship.yml` in this repo for a sample.
4. Push. The pipeline runs on your code, not ours.

Minimal caller:

```yaml
name: secureship
on: [push, pull_request]
permissions: { contents: read, packages: write, id-token: write, security-events: write }
jobs:
  secureship:
    uses: secure-ship/secureship/.github/workflows/secureship-pipeline.yml@v1.0.0
    with: { app-path: ".", image-name: "my-app", build-branch: "main" }
```

## What it does

| Stage | Tool |
|---|---|
| Secrets | Gitleaks |
| SAST | Semgrep (Java, OWASP Top 10) |
| Dependencies and Dockerfile misconfig | Trivy |
| Dockerfile lint | Hadolint |
| Image scan | Grype |
| SBOM | Syft (SPDX) |
| Signing and verification | Cosign (keyless, GitHub OIDC) |

Scan results are uploaded as SARIF to GitHub Code Scanning, and JSON reports are saved as workflow artifacts. The image build, signing and push run only on the branch named in `build-branch`.

## Inputs

All inputs are optional strings.

| Input | Meaning | Built-in default |
|---|---|---|
| `app-path` | Folder with the app and its Dockerfile | `.` |
| `image-name` | Image name in GHCR | `app` |
| `severity-cutoff` | Fail at this severity or above (`CRITICAL`, `HIGH`, `MEDIUM`, `LOW`) | `CRITICAL` |
| `build-branch` | Branch that builds, signs and pushes | `main` |
| `run-gitleaks`, `run-semgrep`, `run-trivy`, `run-hadolint`, `run-grype` | `"true"` or `"false"` | `true` |

## Configuration priority

1. Values in your caller's `with:` block
2. Values in `secureship.yml` at your repo root
3. The built-in defaults above

## Verifying a signed image

```
cosign verify \
  --certificate-identity-regexp "^https://github.com/<owner>/<repo>/" \
  --certificate-oidc-issuer https://token.actions.githubusercontent.com \
  ghcr.io/<owner>/<repo>/<image>:<commit-sha>
```

## Notes

- Make the GHCR package public if you want anyone to verify it without logging in.
- The `vulnerable` and `fixed` branches hold the demo app used to test the pipeline. `docs/flaws.md` lists the planted flaws.
