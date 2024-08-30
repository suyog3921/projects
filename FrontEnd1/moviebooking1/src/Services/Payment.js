import axios from "axios";
import config from "../config";

function loadScript(src) {
    return new Promise((resolve) => {
        const script = document.createElement('script');
        script.src = src;
        script.onload = () => resolve(true);
        script.onerror = () => resolve(false);
        document.body.appendChild(script);
    });
}

export async function paymentStart(amount) {
    try {
        const token = sessionStorage.getItem('token')

        console.log("Payment started");

        // Fetch the order details from your server

        const response = await axios.post(
            `${config.url}/order`,
            { amount }, // Only include the amount in the body
            {
                headers: {
                    Authorization: token // Include the token in the Authorization header
                }
            }
        );

        // Load the Razorpay script
        const scriptLoaded = await loadScript('https://checkout.razorpay.com/v1/checkout.js');
        
        if (!scriptLoaded) {
            throw new Error("Failed to load Razorpay script");
        }

        return new Promise((resolve, reject) => {
            if (response.data.status === "created") {
                var options = {
                    key: "rzp_test_424E58xpMxZkTG", // Enter the Key ID generated from the Dashboard
                    amount: response.data.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
                    currency: "INR",
                    name: "Movie Magic", // Your business name
                    description: "Movie Ticket",
                    image: "", // You can add a logo URL if needed
                    order_id: response.data.order_id, // This is a sample Order ID. Pass the `id` obtained in the response of Step 1
                    handler: function (response) {
                        console.log(response);
                        console.log(response.razorpay_payment_id);
                        console.log(response.razorpay_order_id);
                        console.log(response.razorpay_signature);
                        resolve(response); // Resolve the promise if payment is successful
                    },
                    prefill: {
                        name: "Your name", // Your customer's name
                        email: "test@example.com",
                        contact: "9000090000" // Provide the customer's phone number
                    },
                    notes: {
                        address: "Sunbeam, Hinjewadi"
                    },
                    theme: {
                        color: "#3399cc"
                    }
                };

                var rzp1 = new window.Razorpay(options);
                rzp1.on('payment.failed', function (response) {
                    console.log(response.error.code);
                    console.log(response.error.description);
                    console.log(response.error.source);
                    console.log(response.error.step);
                    console.log(response.error.reason);
                    console.log(response.error.metadata.order_id);
                    console.log(response.error.metadata.payment_id);
                    reject(response); // Reject the promise if payment fails
                });

                rzp1.open();
            }
        });
    } catch (error) {
        console.log('error-response', error);
        throw error; // Rethrow the error to be handled in the caller
    }
}
