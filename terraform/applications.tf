resource "helm_release" "argocd_apps" {
  name       = "argocd-apps"
  repository = "https://argoproj.github.io/argo-helm"
  chart      = "argocd-apps"
  namespace  = "argocd"

  values = [yamlencode({
    applications = {
      notes = {
        namespace = "argocd"
        project   = "default"
        source = {
          repoURL        = var.gitops_repo_url
          targetRevision = var.gitops_branch
          path           = "notes"
        }
        destination = {
          server    = "https://kubernetes.default.svc"
          namespace = "notes"
        }
        syncPolicy = {
          automated   = { prune = false, selfHeal = true }
          syncOptions = ["CreateNamespace=true"]
        }
      }
      policies = {
        namespace = "argocd"
        project   = "default"
        source = {
          repoURL        = var.gitops_repo_url
          targetRevision = var.gitops_branch
          path           = "policies"
        }
        destination = {
          server = "https://kubernetes.default.svc"
        }
        syncPolicy = {
          automated = { prune = false, selfHeal = true }
        }
      }
    }
  })]

  depends_on = [helm_release.argocd, helm_release.kyverno]
}
