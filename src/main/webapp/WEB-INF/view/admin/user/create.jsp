<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Create - Users</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js">
                </script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <!-- jsp reuse code -->
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">

                    <!-- jsp reuse code -->

                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">User</li>

                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create a user</h3>
                                            <hr />
                                            <!-- form de java spring quan sat gia tri cua input -->
                                            <!-- path de dinh danh phan biet tung input -->
                                            <!-- modelAttribute là cách chúng ta convert dữ liệu từ View trả cho controller xử lý -->
                                            <form:form method="post" action="/admin/user/create"
                                                modelAttribute="newUser" class="row" enctype="multipart/form-data">
                                                <c:set var="errorsEmail">
                                                    <form:errors path="email" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorsPassword">
                                                    <form:errors path="password" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorsFullName">
                                                    <form:errors path="fullName" cssClass="invalid-feedback" />
                                                </c:set>
                                                <div class="mb-3 col-md-6 col-12">
                                                    <label class="form-label">Email:</label>
                                                    <form:input type="email"
                                                        class="form-control ${not empty errorsEmail ? 'is-invalid':''} "
                                                        path="email" />
                                                    ${errorsEmail}
                                                </div>
                                                <div class="mb-3 col-md-6 col-12">

                                                    <label class="form-label">Password:</label>
                                                    <form:input type="password"
                                                        class="form-control ${not empty errorsPassword ? 'is-invalid':''} "
                                                        path="password" />
                                                    ${errorsPassword}
                                                </div>

                                                <div class="mb-3 col-md-6 col-12">
                                                    <label class="form-label">Phone number:</label>
                                                    <form:input type="text" class="form-control" path="phoneNumber" />
                                                </div>
                                                <div class="mb-3 col-md-6 col-12">

                                                    <label class="form-label">Full Name:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorsFullName ? 'is-invalid':''}"
                                                        path="fullName" />
                                                    ${errorsFullName}
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Address:</label>
                                                    <form:input type="text" class="form-control " path="address" />
                                                </div>
                                                <div class="mb-3 col-md-6 col-12">
                                                    <label class="form-label">Role:</label>
                                                    <form:select class="form-select" path="role.name">
                                                        <form:option value="ADMIN">ADMIN</form:option>
                                                        <form:option value="USER">USER</form:option>
                                                    </form:select>
                                                </div>

                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="avatarFile" class="form-label">Avatar:</label>
                                                    <input class="form-control" type="file" id="avatarFile"
                                                        accept=".png, .jpg, .jpeg" name="loadFile" />
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                        id="avatarPreview">
                                                </div>
                                                <div class="mb-5 col-12">
                                                    <button type="submit" class="btn btn-primary">Create</button>
                                                </div>
                                            </form:form>
                                        </div>

                                    </div>

                                </div>

                            </div>
                        </main>
                        <!-- jsp reuse code -->
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>

            </body>

            </html>