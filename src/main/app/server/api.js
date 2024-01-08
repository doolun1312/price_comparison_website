document.addEventListener('DOMContentLoaded', function () {
    // Get the "Products" link by its ID
    var productsLink = document.getElementById('productsLink');

    // Get the #app element
    var appElement = document.getElementById('app');

    // Add a click event listener to the "Products" link
    productsLink.addEventListener('click', function (event) {
        // Prevent the default link behavior
        event.preventDefault();

        // Toggle the display property of the #app element
        appElement.style.display = (appElement.style.display === 'block') ? 'none' : 'block';
    });
});

function search(){
    // Get the search query from the input field
    const searchQuery = document.getElementById('searchQuery').value;

    // Replace 'YOUR_API_ENDPOINT' with your actual API endpoint
    const apiEndpoint = '/glasses/';

    // Append the search query to the API endpoint
    const fullApiEndpoint = `${apiEndpoint}${searchQuery}`;

    // Make the Axios GET request
    axios.get(fullApiEndpoint)
        .then(response => {
            // Handle the successful response
            console.log(response.data);
        })
        .catch(error => {
            // Handle errors
            console.error('Error:', error);
        });
}

// Listen for the Enter key press
document.getElementById('searchQuery').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        // Trigger form submission
        search();
    }
});

var app = new Vue({
    el: '#app',
    data: {
        eyewears: [],
        searchQuery: ''
    },
    methods: {
        // Get all the glasses from the web service using Axios.
        loadAllEyewear: function () {
            var localApp = this;
            axios.get('/glasses')
                .then(function (response) {
                    localApp.eyewears = response.data.data;
                    console.log(JSON.stringify(response.data.data));
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        // Trigger search based on the input value
        loadResult: function () {
            var localApp = this;
            axios.get('/' + localApp.searchQuery)
                .then(function (response) {
                    localApp.eyewears = response.data.data;
                    console.log(JSON.stringify(response.data.data));
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    },
    created: function () {
        this.loadAllEyewear();
        setInterval(this.loadAllEyewear, 1000);
    }
});