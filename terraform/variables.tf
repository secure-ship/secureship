variable "kubeconfig_path" {
  description = "Path to the kubeconfig of the target cluster"
  type        = string
  default     = "~/.kube/config"
}

variable "gitops_repo_url" {
  description = "HTTPS URL of the repository holding the gitops branch"
  type        = string
}

variable "gitops_branch" {
  description = "Branch ArgoCD watches"
  type        = string
  default     = "gitops"
}
