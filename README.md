What is this?
=============

Example Spring Boot application which makes REST calls to a remote product catalogue API,
applies some filtering, sorting and data transformation to the result and exposes those
processed data via a REST API on this app.

Design Overview
===============

* The application's main class is `ApplicationMain`.

* The application exposes a single `@RestController` class, `ApiController`.
  This class exposes a single HTTP endpoint `/products` which returns UTF-8 encoded JSON response bodies
  containing matching products from the remote catalogue.
  
  An example response:
```json
[
  {
    "productId": "5517969",
    "title": "Hobbs Seasalter Ditsy Print Dress, Navy/Multi",
    "colorSwatches": [
      {
        "color": "",
        "rgbColor": "0000FF",
        "skuId": "239891777"
      }
    ],
    "nowPrice": "£49",
    "priceLabel": "51% off - now £49"
  },
  {
    "productId": "5401607",
    "title": "Phase Eight Mel Abstract Ruched Dress, Cobalt/White",
    "colorSwatches": [
      {
        "color": "",
        "rgbColor": "",
        "skuId": "239877892"
      }
    ],
    "nowPrice": "£44.50",
    "priceLabel": "50% off - now £44.50"
  }
]
```
  
  The `/products` endpoint accepts an optional query parameter, `labelType`, which tailors the
  product price labels as follows:
  * `labelType` = `ShowWasNow` "Was £20, now £9.99".
  * `labelType` = `ShowWasThenNow` "Was £20, then £15, now £9.99"
  * `labelType` = `ShowPercDiscount` "50% off - now £9.99"

* The data are requested by the `PriceReductionService`, which uses `RemoteCatalogService` to make
  the HTTP calls.

* The data are rendered into JSON by Spring's normal Jackson mapper object with some pre-processing
  of the data performed by the `ProductPresentationService`.

Data modelling
==============

The classes in `com.example.jl.remote.model` and in `com.example.jl.common` model the response data observed
from the remote API. Products have Prices, ColorSwatches. Some products have price ranges rather
than a single price.
These classes do not include fields for all JSON properties observed in the remote response data - only
those data which are required for this exercise. Extraneous data are ignored when Jackson converts
the received JSON to instances of these classes.

These data are later converted into simpler types in `com.example.jl.api` for rendering by
this application's REST API. This is done in order to isolate users of this application from any
possible future changes in the remote API.

Immutable collections
=====================

The application internally uses `io.vavr` collection classes, which support immutable data and built-in
functional programming methods such as `map`, `filter` etc.
