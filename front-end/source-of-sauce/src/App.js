import React, { useState, useEffect } from "react";
import ProductRow from "./ProductRow";
import { call } from "./service/ApiService";
import "./App.css";
import logo from "./source_of_sauce_logo.png";
import { Stack, TextField, Button } from "@mui/material";

function App() {
  // 제품 목록 상태
  const [products, setProducts] = useState([]);
  // 제품 추가 폼 상태
  const [addForm, setAddForm] = useState({
    title: "",
    price: "",
    spicyLevel: "",
    baseType: "",
  });
  // 제품 검색 폼 상태
  const [searchForm, setSearchForm] = useState({
    title: "",
    price: "",
    spicyLevel: "",
    baseType: "",
  });
  // 제품 수정 폼 상태
  const [editForm, setEditForm] = useState({
    id: "",
    title: "",
    price: "",
    spicyLevel: "",
    baseType: "",
  });
  // 삭제할 제품 제목 상태
  const [deleteTitle, setDeleteTitle] = useState("");

  // 컴포넌트 마운트 시 제품 목록 불러오기
  useEffect(() => {
    loadProducts();
  }, []);

  // 제품 목록 불러오기
  function loadProducts() {
    call("/product", "GET").then((response) => {
      setProducts(response?.data || []);
    });
  }

  // 제품 추가 폼 입력 처리
  function handleAddChange(e) {
    const { name, value } = e.target;
    setAddForm((prev) => ({ ...prev, [name]: value }));
  }

  // 제품 추가 요청
  function addProduct() {
    if (
      !addForm.title.trim() ||
      !addForm.price.toString().trim() ||
      !addForm.spicyLevel.toString().trim() ||
      !addForm.baseType.trim()
    ) {
      alert("모든 제품 정보를 입력하세요.");
      return;
    }
    call("/product", "POST", addForm).then(() => {
      loadProducts();
      setAddForm({ title: "", price: "", spicyLevel: "", baseType: "" });
    });
  }

  // 제품 검색 폼 입력 처리
  function handleSearchChange(e) {
    const { name, value } = e.target;
    setSearchForm((prev) => ({ ...prev, [name]: value }));
  }

  // 제품 검색 요청
  function searchProduct() {
    call(`/product?title=${encodeURIComponent(searchForm.title)}`, "GET").then(
      (response) => {
        if (response?.data && response.data.length > 0) {
          const product = response.data[0];
          setSearchForm({
            title: product.title || "",
            price: product.price || "",
            spicyLevel: product.spicyLevel || "",
            baseType: product.baseType || "",
          });
        } else {
          alert("제품을 찾을 수 없습니다.");
          setSearchForm({ title: "", price: "", spicyLevel: "", baseType: "" });
        }
      }
    );
  }

  // 제품 수정 폼 입력 처리
  function handleEditChange(e) {
    const { name, value } = e.target;
    setEditForm((prev) => ({ ...prev, [name]: value }));
  }

  // 수정할 제품 검색 요청
  function searchProductForEdit() {
    call(`/product?title=${encodeURIComponent(editForm.title)}`, "GET").then(
      (response) => {
        if (response?.data && response.data.length > 0) {
          const product = response.data[0];
          setEditForm({
            id: product.id || "",
            title: product.title || "",
            price: product.price || "",
            spicyLevel: product.spicyLevel || "",
            baseType: product.baseType || "",
          });
        } else {
          alert("제품을 찾을 수 없습니다.");
          setEditForm({
            id: "",
            title: "",
            price: "",
            spicyLevel: "",
            baseType: "",
          });
        }
      }
    );
  }

  // 제품 정보 수정 요청
  function editProduct() {
    call("/product", "PUT", editForm).then(() => {
      loadProducts();
      setEditForm({
        id: "",
        title: "",
        price: "",
        spicyLevel: "",
        baseType: "",
      });
    });
  }

  // 제품 삭제 요청 (제목으로 검색 후 삭제)
  function deleteProduct() {
    if (!deleteTitle.trim()) {
      alert("삭제할 제품의 제목을 입력하세요.");
      return;
    }
    // title만 쿼리 파라미터로 전달
    call(`/product?title=${encodeURIComponent(deleteTitle)}`, "DELETE").then(
      () => {
        loadProducts();
        setDeleteTitle("");
      }
    );
  }

  // 제품 목록 행 생성
  const productRows = products.map((product) => (
    <ProductRow key={product.id} product={product} />
  ));

  return (
    <div className="sos-root">
      <div className="sos-logo-wrap">
        <img src={logo} alt="Source of Sauce Logo" className="sos-logo" />
      </div>

      <div className="sos-caption">Source of Sauce 제품 목록</div>

      {/* 제품 목록 테이블 */}
      <table className="product-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Price</th>
            <th>SpicyLevel</th>
            <th>BaseType</th>
            <th>UserId</th>
          </tr>
        </thead>
        <tbody>{productRows}</tbody>
      </table>

      <div className="sos-ui-grid">
        {/* 제품 추가 */}
        <div className="ui-box sos-ui-box">
          <h3 className="sos-ui-title">제품 추가</h3>
          <Stack spacing={2}>
            <TextField
              label="Title"
              name="title"
              value={addForm.title}
              onChange={handleAddChange}
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <TextField
              label="Price"
              name="price"
              value={addForm.price}
              onChange={handleAddChange}
              type="number"
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <TextField
              label="Spicy Level"
              name="spicyLevel"
              value={addForm.spicyLevel}
              onChange={handleAddChange}
              type="number"
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <TextField
              label="Base Type"
              name="baseType"
              value={addForm.baseType}
              onChange={handleAddChange}
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <Button
              variant="contained"
              className="sos-btn"
              onClick={addProduct}
              fullWidth
            >
              제품 추가
            </Button>
          </Stack>
        </div>

        {/* 제품 검색 */}
        <div className="ui-box sos-ui-box">
          <h3 className="sos-ui-title">제품 검색</h3>
          <Stack spacing={2}>
            <TextField
              label="Title"
              name="title"
              value={searchForm.title}
              onChange={handleSearchChange}
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <TextField
              label="Price"
              name="price"
              value={searchForm.price}
              onChange={handleSearchChange}
              type="number"
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
              disabled
            />
            <TextField
              label="Spicy Level"
              name="spicyLevel"
              value={searchForm.spicyLevel}
              onChange={handleSearchChange}
              type="number"
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
              disabled
            />
            <TextField
              label="Base Type"
              name="baseType"
              value={searchForm.baseType}
              onChange={handleSearchChange}
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
              disabled
            />
            <Button
              variant="contained"
              className="sos-btn"
              onClick={searchProduct}
              fullWidth
            >
              제품 검색
            </Button>
          </Stack>
        </div>

        {/* 제품 수정 */}
        <div className="ui-box sos-ui-box">
          <h3 className="sos-ui-title">제품 수정</h3>
          <Stack spacing={2}>
            <TextField
              label="Title"
              name="title"
              value={editForm.title}
              onChange={handleEditChange}
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <TextField
              label="Price"
              name="price"
              value={editForm.price}
              onChange={handleEditChange}
              type="number"
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <TextField
              label="Spicy Level"
              name="spicyLevel"
              value={editForm.spicyLevel}
              onChange={handleEditChange}
              type="number"
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <TextField
              label="Base Type"
              name="baseType"
              value={editForm.baseType}
              onChange={handleEditChange}
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <Button
              variant="contained"
              className="sos-btn"
              onClick={searchProductForEdit}
              fullWidth
            >
              제품 검색
            </Button>
            <Button
              variant="contained"
              className="sos-btn"
              onClick={editProduct}
              fullWidth
            >
              제품 수정
            </Button>
          </Stack>
        </div>

        {/* 제품 삭제 */}
        <div className="ui-box sos-ui-box">
          <h3 className="sos-ui-title">제품 삭제</h3>
          <Stack spacing={2}>
            <TextField
              label="Title"
              value={deleteTitle}
              onChange={(e) => setDeleteTitle(e.target.value)}
              fullWidth
              size="small"
              InputProps={{ className: "sos-input" }}
            />
            <Button
              variant="contained"
              className="sos-btn"
              onClick={deleteProduct}
              fullWidth
            >
              제품 삭제
            </Button>
          </Stack>
        </div>
      </div>

      {/* 카피라이트 푸터 */}
      <footer className="sos-footer">
        © {new Date().getFullYear()} Eunsun Jang. All Rights Reserved.
      </footer>
    </div>
  );
}

export default App;
