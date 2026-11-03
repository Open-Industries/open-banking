# Card Service

Bounded context: **Cards**. Issuance and lifecycle. **The full PAN is never stored or
returned** — tokenization belongs to a PCI-certified vault; this service keeps only the
masked PAN and last four digits.

| Item | Value |
|---|---|
| Port | `8085` (`HTTP_PORT`) |
| Base path | `/api/v1/cards` |
| Owns data | Card (masked), card status |
| Publishes | `card.issued`, `card.blocked` |
