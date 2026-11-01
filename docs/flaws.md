# Planted flaws

| # | Flaw | Location (vulnerable branch) | How it was confirmed | Fix (fixed branch) | Expected tool |
|---|------|------------------------------|----------------------|--------------------|---------------|
| 1 | Hardcoded API key (AWS-style format) | `NotesController.java`, `API_KEY` | Found in source | Constant deleted | Gitleaks, Semgrep |
| 2 | SQL injection | `NotesController.search()`, `/api/notes/search?q=` | `' OR 1=1 --` returned every note (screenshot) | Parameterized query, JWT auth required | Semgrep (later ZAP, WAF) |
| 3 | Stored XSS | `templates/index.html`, `th:utext` on title and content | Injected HTML rendered as markup (screenshot) | `th:text` escapes output | Semgrep (later ZAP) |
| 4 | Container runs as root | `Dockerfile`, no `USER` directive | Read Dockerfile | `USER app` (non-root) | Hadolint, Trivy, Kyverno |
| 5 | Vulnerable dependency | `pom.xml`, `commons-text` 1.9 (CVE-2022-42889) | Read pom.xml | Upgraded to 1.10.0 | Trivy (pom.xml and image) |
| 6 | Weak DB credentials, port 5432 published | `docker-compose.yml`, `application.properties` | Read config | Accepted lab risk, see accepted-risks.md | Trivy config scan (maybe) |
| 7 | Unauthenticated endpoints | All endpoints open on vulnerable | `curl` returned data without login | JWT login, protected endpoints return 403 | ZAP |

## Also on the fixed branch
- Alpine OS packages patched with `apk upgrade`.
- Base images are tagged but not pinned by digest (possible Hadolint/Trivy note).
