<template>
  <div class="add-book-container">
    <el-card class="add-book-card">
      <h2>Thêm sách mới</h2>

      <el-form
        ref="bookFormRef"
        :model="bookForm"
        :rules="rules"
        label-width="120px"
        class="add-book-form"
      >
        <el-form-item label="Tên sách" prop="title">
          <el-input v-model="bookForm.title" placeholder="Nhập tên sách" />
        </el-form-item>

        <el-form-item label="Tác giả" prop="author">
          <el-input v-model="bookForm.author" placeholder="Nhập tên tác giả" />
        </el-form-item>

        <el-form-item label="Thể loại" prop="category">
          <el-select v-model="bookForm.category" placeholder="Chọn thể loại">
            <el-option label="Tiểu thuyết" value="novel" />
            <el-option label="Khoa học" value="science" />
            <el-option label="Lịch sử" value="history" />
            <el-option label="Tâm lý" value="psychology" />
          </el-select>
        </el-form-item>

        <el-form-item label="Giá (VND)" prop="price">
          <el-input-number
            v-model="bookForm.price"
            :min="0"
            :step="1000"
            placeholder="Nhập giá"
          />
        </el-form-item>

        <el-form-item label="Mô tả" prop="description">
          <el-input
            type="textarea"
            :rows="4"
            v-model="bookForm.description"
            placeholder="Nhập mô tả sách"
          />
        </el-form-item>

        <el-form-item label="Ảnh bìa" prop="cover">
          <el-upload
            class="upload-demo"
            action="#"
            :auto-upload="false"
            :on-change="handleFileChange"
          >
            <el-button type="primary">Chọn ảnh</el-button>
            <template #tip>
              <div class="el-upload__tip">Chỉ chấp nhận file ảnh (jpg/png)</div>
            </template>
          </el-upload>

          <img
            v-if="previewUrl"
            :src="previewUrl"
            alt="Preview"
            class="preview-image"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">Thêm sách</el-button>
          <el-button @click="resetForm">Làm mới</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

interface BookForm {
  title: string
  author: string
  category: string
  price: number
  description: string
  cover: File | null
}

const bookForm = ref<BookForm>({
  title: '',
  author: '',
  category: '',
  price: 0,
  description: '',
  cover: null,
})

const previewUrl = ref<string | null>(null)

const bookFormRef = ref<FormInstance>()

const rules: FormRules = {
  title: [{ required: true, message: 'Vui lòng nhập tên sách', trigger: 'blur' }],
  author: [{ required: true, message: 'Vui lòng nhập tác giả', trigger: 'blur' }],
  category: [{ required: true, message: 'Chọn thể loại', trigger: 'change' }],
  price: [{ required: true, message: 'Nhập giá', trigger: 'blur' }],
}

const handleFileChange = (file: any) => {
  bookForm.value.cover = file.raw
  previewUrl.value = URL.createObjectURL(file.raw)
}

const submitForm = async () => {
  if (!bookFormRef.value) return
  await bookFormRef.value.validate((valid) => {
    if (valid) {
      console.log('Book Data:', bookForm.value)
      alert('Thêm sách thành công!')
    } else {
      console.log('Validation failed')
    }
  })
}

const resetForm = () => {
  if (!bookFormRef.value) return
  bookFormRef.value.resetFields()
  previewUrl.value = null
}
</script>

<style scoped>
.add-book-container {
  display: flex;
  justify-content: center;
  padding: 40px;
  background-color: #f5f6fa;
}

.add-book-card {
  width: 600px;
  padding: 20px;
}

.preview-image {
  margin-top: 10px;
  width: 120px;
  height: 160px;
  object-fit: cover;
  border-radius: 8px;
}
</style>
