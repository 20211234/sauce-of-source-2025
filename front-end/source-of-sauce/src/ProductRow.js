import React from "react";

function ProductRow({ product }) {
  return (
    <tr>
      <td className="product-td">{product.id}</td>
      <td className="product-td">{product.title}</td>
      <td className="product-td">{product.price}</td>
      <td className="product-td">{product.spicyLevel}</td>
      <td className="product-td">{product.baseType}</td>
      <td className="product-td">{product.userId}</td>
    </tr>
  );
}

export default ProductRow;
