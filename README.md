![](/img/mysql-k8s.png)

## [spring-boot-mysql-k8s-demo](#spring-boot-mysql-k8s-demo)

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-mysql-k8s-demo/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-mysql-k8s-demo/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?logo=springboot)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)

Run spring boot application and mysql on kubernetes cluster

## [Tech Stack](#tech-stack)

| Component        | Version                     |
|:-----------------|:----------------------------|
| Java (JDK)       | 25 (Temurin / Corretto)     |
| Spring Boot      | 4.1.1                       |
| Spring Data JPA  | Hibernate 7                 |
| MySQL            | 8.0 (tests) / 5.7 (k8s)     |
| Testcontainers   | 2.0.5                       |
| Build tool       | Maven Wrapper (`./mvnw`)    |
| Container image  | `amazoncorretto:25-alpine`  |

## [Prerequisite / Libraries used](#Prerequisite)

* JDK 25
* Docker and kubernetes cluster
* kubectl
* Spring Boot
* Spring Configuration
* Spring REST Controller
* Spring Data JPA
* MySQL
* Lombok
* Testcontainers (integration tests)

## [Compilation command](#compilation-command)

```./mvnw clean package -DskipTests```

## [Running tests](#running-tests)

Integration tests spin up a real MySQL 8.0 database with Testcontainers, so Docker must be running.

```./mvnw clean verify```

## [Build Docker image](#build-docker-image)

```docker build -t spring-boot-mysql-k8s-demo:1.0 .```

## [Verify image creation](#verify-image-creation)

```docker image ls```

## [Install kubernetes objects for spring-boot app and mysql database](#create-k8s)

These files will be available under deploy folder and need to be executed sequentially.

```kubectl apply -f deploy/mysql-credentials.yml```

```kubectl apply -f deploy/mysql-root-credentials.yml```

```kubectl apply -f deploy/mysql-configmap.yml```

```kubectl apply -f deploy/mysql-deployment.yml```

```kubectl apply -f deploy/spring-boot-deployment.yml```

## [Verify deployment objects](#verify-deployment-objects)

> To verify all deployment objects running:

```kubectl get all -o wide```

> Spring boot app log verification:

```kubectl logs -f <podName>```

```Eg: kubectl logs -f spring-boot-mysql-k8s-demo-6444f95584-mfvnz```

## [Verify mysql container and connectivity](#Verify-mysql-container)

> Navigate inside mysql container:

```kubectl exec -it <podName> -- bash```

```Eg: kubectl exec -it mysql-795b8d8fbb-6mjxl -- bash```

> Provide credentials:

```mysql -h mysql -u testuser -p```

> Enter DB password as: testuser@123

> Execute sql commands:

```show databases;```

```use test;```

```show tables;```

```select * from user;``` -> This will return empty records as we have not inserted any data yet.

## [API end-points](#API-end-points)

| Method    | URL                                       | Description                                                                       |
|:----------|:------------------------------------------|:----------------------------------------------------------------------------------|
| `POST`			 | `http://localhost:30163/addUser`		        | Add new user using the User model.	Eg:{"name":"Mark","country":"US"}													 |
| `GET`			  | `http://localhost:30163/users`	           | This returns the list of users from the mysql table.				                          |
| `GET`			  | `http://localhost:30163/findUser/{id}`		  | This returns the user by id.													                                         |
| `GET`			  | `http://localhost:30163/deleteUser/{id}`	 | This will delete the user by id.                                                  |

## [Uninstall kubernetes objects for spring-boot app and mysql database ](#uninstall)

These files will be available under deploy folder and need to be executed sequentially.

```kubectl delete -f deploy/mysql-credentials.yml```

```kubectl delete -f deploy/mysql-root-credentials.yml```

```kubectl delete -f deploy/mysql-configmap.yml```

```kubectl delete -f deploy/mysql-deployment.yml```

```kubectl delete -f deploy/spring-boot-deployment.yml```

## [Removing Docker Image](#removing-docker-image)

> Prints the docker image name and tag

```docker image ls```

> Remove a docker image

```docker image rm <imageName>:<tag>```

`Eg: docker image rm spring-boot-mysql-k8s-demo:1.0`

## [Continuous Integration](#continuous-integration)

The [Java CI with Maven](.github/workflows/maven.yml) workflow runs on every push and pull request to `main` (and can be
triggered manually):

* Sets up Temurin JDK 25 with Maven dependency caching
* Runs `./mvnw -B verify` (compile + Testcontainers integration tests)
* On pushes to `main`, submits the Maven dependency graph to GitHub to improve Dependabot alerts
* Uses least-privilege permissions and cancels superseded runs for the same branch

## [Dependency updates](#dependency-updates)

[Dependabot](.github/dependabot.yml) keeps the project up to date:

| Ecosystem      | Scope                                         | Schedule              | Grouping                                                  |
|:---------------|:----------------------------------------------|:----------------------|:----------------------------------------------------------|
| Maven          | `pom.xml`                                     | Daily 05:00 (WIB)     | Spring Boot, Testcontainers, other minor/patch updates    |
| GitHub Actions | `.github/workflows`                           | Daily 05:00 (WIB)     | All actions in one PR                                     |
| Docker         | `Dockerfile` and Kubernetes manifests `deploy/` | Weekly, Monday 05:00 | Stays on the JDK 25 LTS line for `amazoncorretto`        |

Dependabot PRs use Conventional Commit prefixes (`build(deps)`, `ci(deps)`, `build(docker)`).
