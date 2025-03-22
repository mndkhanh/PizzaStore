<%-- 
    Document   : order
    Created on : Mar 21, 2025, 3:08:15 AM
    Author     : mndkh
--%>

<%@page import="Model.DAO.ProductDAO"%>
<%@page import="Model.DTO.Product"%>
<%@page import="Model.DTO.OrderDetail"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Order Products</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .btn-remove { background: red; color: white; }
            .btn-quantity { background: #007bff; color: white; margin: 0 5px; }
        </style>
    </head>
    <body>

        <!-- Header -->
        <%@include file="shared/header.jsp" %>
        <div class="container mt-4">
            <h2 class="text-center">Your Order</h2>
            <c:if test="${status eq 'UNPAID'}">
                <h5 class="text-center text-primary">Please double-check necessary information & complete payment!</h5>
            </c:if>

            <table class="table table-striped text-center">
                <thead class="table-dark">
                    <tr>
                        <th>Image</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) request.getAttribute("order");
                        if (cart != null && !cart.isEmpty()) {
                            for (OrderDetail detail : cart.values()) {
                                Product product = new ProductDAO().getProductByID(detail.getProductID());
                                int quantity = detail.getQuantity();
                                double totalPrice = product.getUnitPrice() * quantity;
                    %>
                    <tr>
                        <td><img src="<%= product.getImg()%>" alt="Product" width="50"></td>
                        <td><%= product.getProductName()%></td>
                        <td>$<%= String.format("%.2f", product.getUnitPrice())%></td>
                        <td><%= quantity%></td>
                        <td>$<%= String.format("%.2f", totalPrice)%></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6">Your cart is empty.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h5>Freight $${freight}</h5>
                    <h4 class="text-primary">Total Cost $${totalCost}</h4>
                    <p class="text-primary">Ship to: ${shipAddress}</p>
                </div>
                <h3>
                    Status: 
                    <span class="badge 
                          ${status eq 'UNPAID' ? 'bg-warning text-dark' : 
                            status eq 'PAID' ? 'bg-primary' : 
                            status eq 'SUCCESS' ? 'bg-success' : 
                            status eq 'FAILED' ? 'bg-danger' : 'bg-secondary'}">
                              ${status}
                          </span>
                    </h3>
                </div>
            </div>

            <c:if test="${status eq 'UNPAID'}">
                <div class="card mt-4 border shadow-sm" style="max-width: 600px; margin: auto;">
                    <div class="card-header text-center bg-light">
                        <h5 class="mb-0">Payment Information</h5>
                    </div>
                    <div class="card-body d-flex align-items-center justify-content-center gap-3">
                        <img src="https://img.vietqr.io/image/MB-0362718422-qr_only.png?amount=${AMOUNT}&addInfo=ORDER${orderID}PAZISTORE" 
                             alt="Payment QR Code" class="img-fluid" style="max-width: 150px; border: 1px solid #ccc; border-radius: 8px; padding: 5px;">
                        <div>
                            <p class="mb-1 fw-bold">Bank Name: <span class="fw-normal">MB Bank</span></p>
                            <p class="mb-1 fw-bold">Account ID: <span class="fw-normal">0362718422</span></p>
                            <p class="mb-1 fw-bold">Account Name: <span class="fw-normal">Mai Nguyen Duy Khanh</span></p>
                            <p class="mb-1 fw-bold">Amount: <span class="fw-normal">${AMOUNT}</span></p>
                            <p class="mb-0 fw-bold">Description: <span class="fw-normal">ORDER${orderID}PAZISTORE</span></p>
                        </div>
                    </div>

                </div>
            </c:if>
            <input id="orderID" value="${orderID}" hidden>

            <div style="margin-top: 500px;">
                <%@include file="shared/footer.jsp" %>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                const currentStatus = "${status}";
                const oid = "${orderID}";
                const rootProject = "${pageContext.request.contextPath}";
                const urlApi = rootProject + "/user/checkpaymentstatus?oid=" + oid;
                console.log(urlApi);

                let intervalId;

                function checkPaymentStatus() {
                    fetch(urlApi)
                            .then(response => response.json())
                            .then(data => {
                                console.log(data);
                                const status = data.status;
                                const isDataChangedToPaid = data.isDataChangedToPaid;

                                if (status === "PAID" && isDataChangedToPaid === "YES") {
                                    clearInterval(intervalId);
                                    alert("Successfully completed payment. Please reload to see updates!");
                                    window.location.href = rootProject + "/user/vieworder?oid=" + oid;
                                } else if (status === "FAILED") {
                                    clearInterval(intervalId);
                                    alert("There was an error completing the payment.");
                                    window.location.href = rootProject + "/user/vieworder?oid=" + oid;
                                }
                            })
                            .catch(error => console.error("Error fetching payment status: ", error));
                }

                if (currentStatus === "UNPAID") {
                    intervalId = setInterval(checkPaymentStatus, 2000);
                }


            </script>
        </body>
    </html>
