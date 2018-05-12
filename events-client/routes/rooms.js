var express = require('express');
var router = express.Router();

const rooms = [
  {
    id: 1,
    products: {
      shoesSize: 32,
      tShirtSize: 'S'
    }
  },
  {
    id: 2,
    products: {
      shoesSize: 41,
      tShirtSize: 'XL'
    }
  },
  {
    id: 3,
    products: {
      shoesSize: 38,
      tShirtSize: 'M'
    }
  }
];

router.get('/:roomId', function(req, res, next) {
  res.send('You connected to room ' + rooms[req.params.roomId-1].id);
});

router.get('/:roomId/products', function(req, res, next) {
  res.send('Products' + JSON.stringify(rooms[req.params.roomId].products));
});

module.exports = router;
