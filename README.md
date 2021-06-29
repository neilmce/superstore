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

* The data are requested by the `ProductService`, which uses `RemoteCatalogService` to make
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
This was done because vavr has better support for this style of programming than Java's own stream classes.

Prices vs. Price ranges
=======================
There is an interesting quirk at the centre of this application.
The application aims to return products that have had their prices reduced, sorted by the magnitude
of that price reduction.
For many products this is easy to understand: they have a 'was' price and a 'now' price with the latter
being lower. Some products have intermediate prices but that doesn't add complexity.
The unusual case is a product which has a price _range_. For these products it is possible to detect
unambiguous price reductions e.g. if the 'now' price range has limits both of which are lower
than those of the 'was' range. So I have included these products in the response.
But for these products the absolute value of the price reduction, price.was - price.now is not clear.
Therefore it's unclear where these prices would appear in a list sorted by reduction magnitude.
I have arbitrarily sorted them low in that list.

To run
======
Launch the spring boot application in the usual way and then point your browser at e.g.
`http://localhost:8080/products?labelType=WasThenNow` (The query param is optional.
For allowed values, see above.)