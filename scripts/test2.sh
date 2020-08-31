curl 'https://api.flow.io/checkouts' \
  -H 'authorization: session F51K1GrlCvRMkMypgYDDDVWL94SpTnhNgORbA5eI7P6BUc4KLfGwWTFLB2GN4oQU'\
  -H 'content-type: application/json' \
  --data-binary $'{"organization": "shopify-dev-sandbox", "order": { "items": [{"number": "13211230175332", "quantity": 1 }] }}' \
  --compressed
