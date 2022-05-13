# Team5-P2
P2-Groceries-Instacart
# P2 Presentation Date
Monday  - May 23rd 2022
# Environment Variables
These are the environment variables required to run the API.
### Maps/Payments API
`MAPS_API_KEY`
The access key for Google Maps API

`STRIPE_KEY`
The access key for Stripe API
### Notification API
`EMAIL_USER`
The email username used by the API.  
`EMAIL_PASS`
The password of the email user.  
`TWILIO_ACCOUNT_SID`
The unique string identifier for the Twilio API.  
`TWILIO_ACCOUNT_TOKEN`
The access token for the Twilio API.  
`TWILIO_PHONE_NUMBER`
The phone number being used to send SMS messages.
# Using the API
### Notification API
Request an email message to be sent to recipient.
```
Map<String, Object> map = new HashMap<>();
RestTemplate restTemplate = new RestTemplateBuilder().build();
map.put("recipient", recipient);
map.put("subject", subject);
map.put("message", body);
restTemplate.postForEntity(url, map, ResponseEntity.class);
```
Request an SMS message to be sent to recipient.
```
Map<String, Object> map = new HashMap<>();
RestTemplate restTemplate = new RestTemplateBuilder().build();
map.put("recipient", recipient);
map.put("message", message);
restTemplate.postForEntity(url, map, ResponseEntity.class);
```

# Test3 solution
Roshney

James

Daewoon

-Nisarg P.

Tony



