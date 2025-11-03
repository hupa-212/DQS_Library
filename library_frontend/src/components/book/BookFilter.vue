<template>
  <div class="sidebar">
    <h3 class="sidebar-title">NHÓM SẢN PHẨM</h3>
    <ul class="menu">
      <li>Tất cả Nhóm Sản Phẩm</li>
      <li>Sách Tiếng Việt</li>
      <li>Văn học</li>
      <li class="active">Tiểu thuyết</li>
      <li>Truyện Ngắn - Tản Văn</li>
      <li>Light Novel</li>
      <li>Xem Thêm</li>
    </ul>

    <h3 class="sidebar-title">GIÁ</h3>
    <ul class="menu">
      <li><input type="checkbox" /> 0 - 150.000đ</li>
      <li><input type="checkbox" /> 150.000đ - 300.000đ</li>
      <li><input type="checkbox" /> 300.000đ - 500.000đ</li>
      <li><input type="checkbox" /> 500.000đ - 700.000đ</li>
      <li><input type="checkbox" /> 700.000đ trở lên</li>
    </ul>

    <h3 class="sidebar-title">GENRES</h3>
    <ul class="menu">
      <li><input type="checkbox" /> Comedy</li>
      <li><input type="checkbox" /> Drama</li>
      <li><input type="checkbox" /> Adventure</li>
      <li><input type="checkbox" /> School Life</li>
    </ul>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.sidebar {
  width: 220px;
  padding: 10px;
  background: #fff;
  border-right: 1px solid #eee;
}

.sidebar-title {
  font-size: 15px;
  font-weight: 600;
  margin: 10px 0;
}

.menu {
  list-style: none;
  padding: 0;
  margin-bottom: 20px;
}

.menu li {
  margin: 6px 0;
  font-size: 14px;
  cursor: pointer;
}

.menu li.active {
  color: #409eff;
  font-weight: 600;
}
</style>
