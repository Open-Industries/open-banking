# Open Banking — developer entry points
.PHONY: build test verify dev-ledger dev-account dev-payment compose-up compose-down

build:            ## Build all modules without tests
	mvn -q package -DskipTests

test:             ## Run all tests
	mvn test

verify:           ## Full verification (life-cycle gate G3)
	mvn verify

dev-ledger:       ## Run the ledger service in dev mode
	mvn -pl services/ledger-service quarkus:dev

dev-account:
	mvn -pl services/account-service quarkus:dev

dev-payment:
	mvn -pl services/payment-service quarkus:dev

compose-up:       ## Start local backing services (PostgreSQL, Kafka, Keycloak)
	docker compose up -d

compose-down:
	docker compose down
