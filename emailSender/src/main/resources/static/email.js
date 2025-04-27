document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("emailForm").addEventListener("submit", async function (e) {
        e.preventDefault();
        const email = document.getElementById("email").value;
        const username = document.getElementById("username").value;
        const type = document.getElementById("type").value;

        const API_BASE = "http://localhost:8080"; 
        let url = "";
        
        // Obtener los valores de producto y stock solo si la opción seleccionada es "lowStock"
        let productName = "";
        let currentStock = 0;

        if (type === "lowStock") {
            productName = document.getElementById("productName").value;
            currentStock = document.getElementById("currentStock").value;
        }

        if (type === "newAccount") {
            url = `${API_BASE}/newAccount/${email}/${username}`;
        } else if (type === "forgotPassword") {
            url = `${API_BASE}/forgotPassword/${email}/${username}`;
        } else if (type === "sendActivationEmail") {
            url = `${API_BASE}/sendActivationEmail/${email}/${username}`;
        } else if (type === "passwordChanged") {
            url = `${API_BASE}/passwordChanged/${email}/${username}`;
        } else if (type === "lowStock") {
            url = `${API_BASE}/lowStock/${email}/${productName}/${currentStock}`;
        } else if (type === "purchaseNotification") {
            const productsInput = document.getElementById("products").value.trim();
            
            // Convertir la entrada en una cadena para enviarla
            const productListString = productsInput;  

            url = `${API_BASE}/purchaseNotification/${email}/${productListString}`;
        }

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Error en la solicitud: ' + response.status);
            }
            const result = await response.text();
            document.getElementById("response").innerText = result;
        } catch (error) {
            console.error('Error:', error);
            document.getElementById("response").innerText = "Hubo un error al enviar el correo.";
        }
    });
});


document.addEventListener("DOMContentLoaded", function() {
    const typeSelect = document.getElementById("type");
    const lowStockFields = document.getElementById("lowStockFields");
    const purchaseFields = document.getElementById("purchaseFields");
    const emailForm = document.getElementById("emailForm");

    // Mostrar u ocultar campos según el tipo seleccionado
    typeSelect.addEventListener("change", function() {
        if (typeSelect.value === "lowStock") {
            lowStockFields.style.display = "block";
            purchaseFields.style.display = "none";
        } else if (typeSelect.value === "purchaseNotification") {
            lowStockFields.style.display = "none";
            purchaseFields.style.display = "block";
        } else {
            lowStockFields.style.display = "none";
            purchaseFields.style.display = "none";
        }
    });

    // Validar formulario antes de enviarlo
    emailForm.addEventListener("submit", function(e) {
        const email = document.getElementById("email").value;
        const type = document.getElementById("type").value;

        if (type === "lowStock") {
            const productName = document.getElementById("productName").value;
            const currentStock = document.getElementById("currentStock").value;
            
            if (!productName || !currentStock) {
                alert("Por favor, completa todos los campos de bajo stock.");
                e.preventDefault();  
                return;
            }
        } else if (type === "purchaseNotification") {
            const products = document.getElementById("products").value;

            if (!products) {
                alert("Por favor, ingresa la lista de productos.");
                e.preventDefault();  // Detener el envío
                return;
            }

            const productList = products.trim(); 

            console.log("Product List:", productList);  
        }
        
    });
});
