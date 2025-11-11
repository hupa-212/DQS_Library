<template>
  <div class="manage-container">
    <AdminMenu />

    <div class="main-content">
      <div class="content-wrapper">
        <h2 class="title">📂 Category Management</h2>

        <!-- Add New Category -->
        <div class="add-category">
          <el-input
            v-model="newCategoryName"
            placeholder="Enter new category name"
            style="width: 300px; margin-right: 10px"
          />
          <el-button type="primary" @click="addCategory">Add</el-button>
        </div>

        <!-- Category Table -->
        <el-table :data="categories" border stripe style="width: 100%; margin-top: 20px">
          <el-table-column prop="id" label="ID" width="80" align="center" />
          <el-table-column label="Category Name">
            <template #default="{ row }">
              <div v-if="editingId === row.id">
                <el-input
                  v-model="editCategoryName"
                  size="small"
                  style="width: 250px"
                />
              </div>
              <div v-else>{{ row.name }}</div>
            </template>
          </el-table-column>

          <el-table-column label="Actions" width="180" align="center">
            <template #default="{ row }">
              <div v-if="editingId === row.id">
                <el-button
                  type="success"
                  size="small"
                  @click="saveEdit(row.id)"
                >
                  Save
                </el-button>
                <el-button
                  type="info"
                  size="small"
                  @click="cancelEdit"
                >
                  Cancel
                </el-button>
              </div>
              <div v-else>
                <el-button
                  type="primary"
                  size="small"
                  @click="startEdit(row)"
                >
                  Edit
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue"
import axios from "axios"
import { ElMessage } from "element-plus"
import AdminMenu from "@/components/admin/AdminMenu.vue"

const categories = ref<any[]>([])
const newCategoryName = ref("")
const editingId = ref<number | null>(null)
const editCategoryName = ref("")

// ✅ computed: disable nút Add nếu input trống hoặc chỉ toàn khoảng trắng
const isAddDisabled = computed(() => !newCategoryName.value.trim())

// ✅ Load danh sách category
const loadCategories = async () => {
  try {
    const res = await axios.get("http://localhost:8080/api/categories")
    categories.value = res.data.data
  } catch (err) {
    console.error(err)
    ElMessage.error("Failed to load categories")
  }
}

// ✅ Thêm category (có token)
const addCategory = async () => {
  if (isAddDisabled.value) {
    ElMessage.warning("Please enter a valid category name")
    return
  }

  const token = sessionStorage.getItem("token") || localStorage.getItem("token")
  if (!token) {
    ElMessage.error("You must be logged in as admin!")
    return
  }

  try {
    await axios.post(
      "http://localhost:8080/api/categories",
      { name: newCategoryName.value.trim() },
      {
        headers: {
          Authorization: `Bearer ${token}`, // ✅ thêm token vào header
          "Content-Type": "application/json",
        },
      }
    )
    ElMessage.success("Category added successfully!")
    newCategoryName.value = ""
    await loadCategories()
  } catch (err: any) {
    console.error(err)
    if (err.response?.status === 403) {
      ElMessage.error("You do not have permission to add categories.")
    } else {
      ElMessage.error("Failed to add category.")
    }
  }
}

// ✅ Bắt đầu chỉnh sửa
const startEdit = (row: any) => {
  editingId.value = row.id
  editCategoryName.value = row.name
}

// ✅ Hủy chỉnh sửa
const cancelEdit = () => {
  editingId.value = null
  editCategoryName.value = ""
}

// ✅ Lưu chỉnh sửa
const saveEdit = async (id: number) => {
  if (!editCategoryName.value.trim()) {
    ElMessage.warning("Category name cannot be empty")
    return
  }

  const token = sessionStorage.getItem("token") || localStorage.getItem("token")
  if (!token) {
    ElMessage.error("You must be logged in as admin!")
    return
  }

  try {
    await axios.put(
      `http://localhost:8080/api/categories/${id}`,
      { name: editCategoryName.value.trim() },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    )
    ElMessage.success("Category updated successfully!")
    editingId.value = null
    await loadCategories()
  } catch (err: any) {
    console.error(err)
    ElMessage.error("Failed to update category")
  }
}

onMounted(() => {
  loadCategories()
})
</script>

<style scoped>
.manage-container {
  display: flex;
  min-height: 100vh;
}

.manage-container > *:first-child {
  flex: 0 0 250px;
}

.main-content {
  flex: 1;
  background-color: #f5f5f5;
  padding: 20px;
}

.content-wrapper {
  background-color: #fff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 16px;
}

.add-category {
  display: flex;
  align-items: center;
}
</style>
