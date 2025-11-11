<template>
  <el-dialog
    v-model="visible"
    :title="'Edit Book: ' + (form.title || '')"
    width="500px"
  >
    <el-form :model="form" ref="formRef" label-width="120px">
      <el-form-item label="Title" prop="title">
        <el-input v-model="form.title" />
      </el-form-item>

      <el-form-item label="Author" prop="author">
        <el-input v-model="form.author" />
      </el-form-item>

      <el-form-item label="ISBN" prop="isbn">
        <el-input v-model="form.isbn" />
      </el-form-item>

      <el-form-item label="Price" prop="price">
        <el-input-number v-model="form.price" :min="0" />
      </el-form-item>

      <el-form-item label="Quantity" prop="quantity">
        <el-input-number v-model="form.quantity" :min="0" />
      </el-form-item>

      <el-form-item label="Category" prop="categoryId">
        <el-select v-model="form.categoryId" placeholder="Select category">
          <el-option
            v-for="cat in categories"
            :key="cat.id"
            :label="cat.name"
            :value="cat.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="Cover URL" prop="coverImageUrl">
        <el-input v-model="form.coverImageUrl" />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="visible = false">Cancel</el-button>
      <el-button type="primary" @click="submitForm">Save</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

interface Category {
  id: number
  name: string
}

interface BookForm {
  id?: number
  title: string
  author: string
  isbn: string
  price: number
  quantity: number
  categoryId?: number
  coverImageUrl: string
}

const visible = ref(false)
const formRef = ref()
const form = reactive<BookForm>({
  id: undefined,
  title: '',
  author: '',
  isbn: '',
  price: 0,
  quantity: 0,
  categoryId: undefined,
  coverImageUrl: ''
})

const categories = ref<Category[]>([])
const emit = defineEmits<{
  (e: 'updated', book: any): void
}>()

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/categories')
    categories.value = res.data.data || res.data
  } catch (err) {
    console.error('Failed to load categories', err)
  }
})

// Mở dialog từ parent
function openDialog(book: any) {
  Object.assign(form, {
    id: book.id,
    title: book.title,
    author: book.author,
    isbn: book.isbn,
    price: book.price,
    quantity: book.quantity,
    categoryId: book.category?.id,
    coverImageUrl: book.coverImageUrl
  })
  visible.value = true
}

// Submit form
async function submitForm() {
  try {
    if (!form.id) return
    const url = `http://localhost:8080/api/books/${form.id}`

    await axios.put(url, form)
    ElMessage.success('Book updated successfully!')
    visible.value = false
    emit('updated', { ...form, category: categories.value.find(c => c.id === form.categoryId) })
  } catch (err) {
    console.error('Error updating book:', err)
    ElMessage.error('Failed to update book!')
  }
}


defineExpose({ openDialog })
</script>
