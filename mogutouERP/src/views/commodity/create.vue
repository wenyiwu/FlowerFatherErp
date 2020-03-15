<template>
  <div class="app-container">
    <el-form v-enterToNext="true" ref="postForm" :model="postForm" :rules="rules" status-icon label-width="80px" class="demo-ruleForm">
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="postForm.name"/>
      </el-form-item>
      <el-form-item label="分类" prop="classify">
        <el-input v-model="postForm.classify"/>
      </el-form-item>
      <el-form-item label="等级" prop="level">
        <el-input v-model="postForm.level"/>
      </el-form-item>
      <el-form-item label="颜色" prop="colour">
        <el-input v-model="postForm.colour"/>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" size="mini" @click="submitForm">提交</el-button>
        <el-button size="mini" @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import { createCommodity } from '@/api/commodity'

export default {
  data() {
    return {
      loading: false,
      postForm: {
        name: '',
        level: '',
        classify: '',
        colour: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        level: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        classify: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        colour: [
          { required: true, message: '请输入', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    submitForm() {
      this.$refs['postForm'].validate(valid => {
        if (valid) {
          this.loading = true
          createCommodity(this.postForm)
            .then(response => {
              console.log(response)
              this.loading = false
              this.resetForm()
              this.$message.success('创建成功')
            })
            .catch(() => {
              this.loading = false
              this.$message.error('创建失败')
            })
        }
      })
    },
    resetForm() {
      this.postForm.name = ''
      this.postForm.level = ''
      this.postForm.classify = ''
      this.postForm.colour = ''
    }
  }
}
</script>
