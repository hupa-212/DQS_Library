<template>
  <div class="add-book-form">
    <el-card class="form-card">
      <h2 class="title">Add New Book</h2>

      <el-form
        :model="form"
        :rules="rules"
        ref="formRef"
        label-width="140px"
        label-position="left"
      >
        <!-- Title -->
        <el-form-item label="Title" prop="title">
          <el-input v-model="form.title" placeholder="Enter book title" />
        </el-form-item>

        <!-- Author -->
        <el-form-item label="Author" prop="author">
          <el-input v-model="form.author" placeholder="Enter author name" />
        </el-form-item>

        <!-- ISBN -->
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="form.isbn" placeholder="Enter valid ISBN" />
        </el-form-item>

        <!-- Category -->
        <el-form-item label="Category" prop="categoryName">
          <el-select
            v-model="form.categoryName"
            placeholder="Select or type category"
            filterable
            allow-create
            clearable
          >
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.name"
            />
          </el-select>
          <div v-if="isNewCategory" class="new-category-msg">
            ⚠️ New category — will be added automatically
          </div>
        </el-form-item>

        <!-- Price -->
        <el-form-item label="Price" prop="price">
          <el-input-number v-model="form.price" :min="0.01" :step="0.01" />
        </el-form-item>

        <!-- Quantity -->
        <el-form-item label="Quantity" prop="quantity">
          <el-input-number v-model="form.quantity" :min="0" />
        </el-form-item>

        <!-- Cover Image -->
        <el-form-item label="Cover Image URL" prop="coverImageUrl">
          <el-input v-model="form.coverImageUrl" placeholder="https://..." />
        </el-form-item>

        <!-- Description -->
        <el-form-item label="Description" prop="description">
          <el-input
            type="textarea"
            v-model="form.description"
            placeholder="Enter book description"
          />
        </el-form-item>

        <!-- Buttons -->
        <el-form-item>
          <el-button type="primary" @click="submitForm">Add Book</el-button>
          <el-button @click="resetForm">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { getCategories, createCategory, createBook } from '@/api/bookAPI'

interface BookForm {
  title: string
  author: string
  isbn: string
  categoryName: string
  price: number
  quantity: number
  description: string
  coverImageUrl: string
}

const form = ref<BookForm>({
  title: '',
  author: '',
  isbn: '',
  categoryName: '',
  price: 0,
  quantity: 0,
  description: '',
  coverImageUrl: '',
})

const categories = ref<{ id: number; name: string }[]>([])
const formRef = ref()

// Validation rules
const rules = {
  title: [{ required: true, message: 'Please input title', trigger: 'blur' }],
  author: [{ required: true, message: 'Please input author', trigger: 'blur' }],
  isbn: [
    { required: true, message: 'Please input ISBN', trigger: 'blur' },
    {
      pattern:
        /^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$/,
      message: 'Invalid ISBN format',
      trigger: 'blur',
    },
  ],
  categoryName: [{ required: true, message: 'Please select or type category', trigger: 'change' }],
  price: [
    { required: true, message: 'Please input price', trigger: 'blur' },
    { type: 'number', min: 0.01, message: 'Price must be at least 0.01' },
  ],
  quantity: [
    { required: true, message: 'Please input quantity', trigger: 'blur' },
    { type: 'number', min: 0, message: 'Quantity cannot be negative' },
  ],
  coverImageUrl: [
    { max: 500, message: 'URL must not exceed 500 characters', trigger: 'blur' },
    { pattern: /^(https?:\/\/)?.*/, message: 'Must be a valid URL', trigger: 'blur' },
  ],
  description: [{ max: 1000, message: 'Description too long', trigger: 'blur' }],
}

// Load categories
onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/categories')
    categories.value = res.data?.data || []
  } catch (err) {
    console.error('Error fetching categories:', err)
  }
})

// Check if user is creating a new category
const isNewCategory = computed(() => {
  if (!form.value.categoryName) return false
  return !categories.value.some(c => c.name === form.value.categoryName)
})

// Submit
const submitForm = () => {

  (formRef.value as any).validate(async (valid: boolean) => {
    if (!valid) {
      console.warn("❌ Form validation failed!");
      return;
    }

    try {
      let categoryId: number;

      if (isNewCategory.value) {
        console.log("🆕 Creating new category:", form.value.categoryName);
        const newCategory = await createCategory({ name: form.value.categoryName });
        console.log("✅ New category created:", newCategory.data);
        categoryId = newCategory.data.id;
        ElMessage.info(`New category "${form.value.categoryName}" created automatically`);
      } else {
        const existing = categories.value.find(c => c.name === form.value.categoryName);
        if (!existing) {
          console.error("⚠️ Category not found:", form.value.categoryName);
          return;
        }
        console.log("📚 Existing category found:", existing);
        categoryId = existing.id;
      }

      const bookPayload = {
        title: form.value.title,
        author: form.value.author,
        isbn: form.value.isbn,
        categoryId,
        price: form.value.price,
        quantity: form.value.quantity,
        description: form.value.description,
        coverImageUrl: form.value.coverImageUrl,
      };

      const res = await createBook(bookPayload);

      ElMessage.success("Book added successfully!");
      resetForm();

      categories.value = await getCategories();
    } catch (err) {
      console.error("❌ Error while adding book:", err);
      ElMessage.error("Failed to add book");
    }
  });
};


const resetForm = () => {
  (formRef.value as any).resetFields()
}
</script>

<style scoped>
.add-book-form {
  display: flex;
  justify-content: center;
  padding: 40px;
}

.form-card {
  width: 650px;
  padding: 30px;
}

.title {
  font-weight: 600;
  font-size: 22px;
  margin-bottom: 20px;
  text-align: center;
}

.new-category-msg {
  color: #e6a23c;
  font-size: 13px;
  margin-top: 4px;
}
</style>
