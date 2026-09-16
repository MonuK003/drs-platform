# transaction-service (MOCK)

This is a **minimal stub**, not the real Transaction Service. It exists only so the
Dispute Service can be developed and integration-tested locally without a live
dependency on the actual Transaction Service.

## Why this exists

If you're primarily working on the Dispute Service, you don't need to reimplement
the whole Transaction Service. You just need something that speaks the same
contract on one endpoint, deterministically, so your tests are repeatable.

## What it exposes

```
POST /transactions/validate-for-dispute
```

### Request body
```json
{
  "transactionId": "TXN12345",
  "accountId": "ACC001",
  "amount": 250.00,
  "currency": "USD",
  "disputeCaseId": null
}
```

### Response body
```json
{
  "transactionId": "TXN12345",
  "valid": true,
  "reasonCode": "ELIGIBLE",
  "message": "Transaction TXN12345 is eligible for dispute",
  "transactionAmount": 250.00,
  "currency": "USD",
  "transactionStatus": "SETTLED",
  "transactionDate": "2026-08-15T10:00:00Z",
  "withinDisputeWindow": true
}
```

## Mock behavior (by transactionId prefix)

This is how you drive different scenarios in your Dispute Service tests without
a database — just change the `transactionId` you send:

| transactionId prefix | Result                                    | reasonCode              |
|-----------------------|-------------------------------------------|--------------------------|
| `NF...`               | Transaction not found                     | `TRANSACTION_NOT_FOUND`  |
| `DUP...`              | Transaction already has an open dispute   | `ALREADY_DISPUTED`       |
| `OLD...`              | Found, but outside the 90-day dispute window | `OUTSIDE_DISPUTE_WINDOW` |
| anything else         | Valid and eligible for dispute            | `ELIGIBLE`                |

Example: sending `transactionId: "DUP-001"` will always return `ALREADY_DISPUTED`,
so you can write a test asserting your Dispute Service correctly rejects/handles
duplicate dispute attempts.

## Running it

```bash
mvn spring-boot:run
```

Runs on port `8084` by default (see `application.yml`). Point your Dispute
Service's Transaction Service client at `http://localhost:8081`.

## What's intentionally NOT here

- No database / persistence
- No auth
- No other Transaction Service endpoints (transaction history, refunds, etc.)
- No real business logic — just deterministic canned responses

When the real Transaction Service is available, swap the base URL in the
Dispute Service's client config. The request/response contract here is designed
to match what the real `validate-for-dispute` endpoint is expected to return,
so no Dispute Service code should need to change.
