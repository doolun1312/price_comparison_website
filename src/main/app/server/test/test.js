const chai = require('chai');
const chaiHttp = require('chai-http');
const server = require('../server');  // Update the path accordingly
const should = chai.should();

chai.use(chaiHttp);

describe('Server', function () {

    describe('GET /glasses', function () {
        it('should return all eyewear', function (done) {
            chai.request(server)
                .get('/glasses')
                .end(function (err, res) {
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.should.have.property('totNumItems');
                    res.body.should.have.property('data');
                    done();
                });
        });
    });

    describe('GET /glasses/:id', function () {
        it('should return eyewear with a specific ID', function (done) {
            chai.request(server)
                .get('/glasses/1')  // Replace 1 with an actual eyewear ID
                .end(function (err, res) {
                    res.should.have.status(200);
                    res.body.should.be.a('array');
                    done();
                });
        });
    });

    describe('GET /glasses/:id', function () {
        it('should return eyewear with the same brand and model as the specified ID', function (done) {
            chai.request(server)
                .get('/glasses/4')  // Replace 1 with an actual eyewear ID
                .end(function (err, res) {
                    res.should.have.status(200);
                    res.body.should.be.a('array');
                    done();
                });
        });
    });

    describe('GET /glasses/:query', function () {
        it('should return eyewear matching the search term', function (done) {
            chai.request(server)
                .get('/glasses/ray')  // Replace 'sunglasses' with an actual search term
                .end(function (err, res) {
                    res.should.have.status(200);
                    res.body.should.be.a('object');
                    res.body.should.have.property('data');
                    done();
                });
        });
    });

    describe('GET unknown path', function () {
        it('should not return any data for an unknown path', function (done) {
            chai.request(server)
                .get('/unknownpath')
                .end(function (err, res) {
                    res.body.should.be.empty;
                    done();
                });
        });
    });



    // Add more test cases as needed

});
