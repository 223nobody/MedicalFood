<template>
  <div class="markdown-renderer" v-html="renderedHtml"></div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'

// 定义props
const props = defineProps<{
  content: string
}>()

// 初始化markdown-it实例
const md = new MarkdownIt({
  html: true,
  linkify: true,
  typographer: true,
  highlight: function (str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        const highlighted = hljs.highlight(str, { language: lang }).value
        return `<pre class="hljs"><code class="language-${lang}">${highlighted}</code></pre>`
      } catch (__) {
        // 忽略错误
      }
    }
    return `<pre class="hljs"><code>${md.utils.escapeHtml(str)}</code></pre>`
  }
})

// 渲染Markdown内容
const renderedHtml = computed(() => {
  if (!props.content) return ''
  return md.render(props.content)
})

// 组件挂载后添加复制按钮
onMounted(() => {
  addCopyButtons()
})

// 为代码块添加复制按钮
const addCopyButtons = () => {
  setTimeout(() => {
    const codeBlocks = document.querySelectorAll('.markdown-renderer pre.hljs')
    codeBlocks.forEach((block) => {
      // 检查是否已经添加了复制按钮
      if (block.querySelector('.copy-button')) return

      const button = document.createElement('button')
      button.className = 'copy-button'
      button.textContent = '复制'
      button.onclick = () => {
        const code = block.querySelector('code')?.textContent || ''
        navigator.clipboard.writeText(code).then(() => {
          button.textContent = '已复制!'
          button.classList.add('copied')
          setTimeout(() => {
            button.textContent = '复制'
            button.classList.remove('copied')
          }, 2000)
        })
      }
      block.appendChild(button)
    })
  }, 100)
}

// 监听内容变化，重新添加复制按钮
import { watch } from 'vue'
watch(
  () => props.content,
  () => {
    addCopyButtons()
  }
)
</script>

<style scoped>
/* ==================== Markdown容器 ==================== */
.markdown-renderer {
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  word-wrap: break-word;
}

/* ==================== 标题样式 ==================== */
.markdown-renderer :deep(h1) {
  font-size: 2em;
  font-weight: 700;
  margin: 1.5em 0 0.8em;
  padding-bottom: 0.3em;
  border-bottom: 2px solid #10b981;
  color: #1a1a1a;
}

.markdown-renderer :deep(h2) {
  font-size: 1.75em;
  font-weight: 700;
  margin: 1.3em 0 0.7em;
  padding-bottom: 0.25em;
  border-bottom: 1px solid #e5e7eb;
  color: #1a1a1a;
}

.markdown-renderer :deep(h3) {
  font-size: 1.5em;
  font-weight: 600;
  margin: 1.2em 0 0.6em;
  color: #2d3748;
}

.markdown-renderer :deep(h4) {
  font-size: 1.25em;
  font-weight: 600;
  margin: 1em 0 0.5em;
  color: #2d3748;
}

.markdown-renderer :deep(h5) {
  font-size: 1.1em;
  font-weight: 600;
  margin: 0.9em 0 0.4em;
  color: #4a5568;
}

.markdown-renderer :deep(h6) {
  font-size: 1em;
  font-weight: 600;
  margin: 0.8em 0 0.3em;
  color: #4a5568;
}

/* ==================== 段落样式 ==================== */
.markdown-renderer :deep(p) {
  margin: 1em 0;
  line-height: 1.8;
}

/* ==================== 列表样式 ==================== */
.markdown-renderer :deep(ul),
.markdown-renderer :deep(ol) {
  margin: 1em 0;
  padding-left: 2em;
}

.markdown-renderer :deep(li) {
  margin: 0.5em 0;
  line-height: 1.6;
}

.markdown-renderer :deep(ul) {
  list-style-type: disc;
}

.markdown-renderer :deep(ul ul) {
  list-style-type: circle;
}

.markdown-renderer :deep(ul ul ul) {
  list-style-type: square;
}

.markdown-renderer :deep(ol) {
  list-style-type: decimal;
}

/* ==================== 引用块样式 ==================== */
.markdown-renderer :deep(blockquote) {
  margin: 1.5em 0;
  padding: 1em 1.5em;
  background: #f7fafc;
  border-left: 4px solid #10b981;
  border-radius: 0 8px 8px 0;
  color: #4a5568;
  font-style: italic;
}

.markdown-renderer :deep(blockquote p) {
  margin: 0.5em 0;
}

/* ==================== 代码样式 ==================== */
.markdown-renderer :deep(code) {
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.9em;
}

/* 行内代码 */
.markdown-renderer :deep(p code),
.markdown-renderer :deep(li code),
.markdown-renderer :deep(td code) {
  background: #f1f5f9;
  color: #e53e3e;
  padding: 0.2em 0.4em;
  border-radius: 4px;
  font-weight: 500;
}

/* 代码块 */
.markdown-renderer :deep(pre) {
  position: relative;
  margin: 1.5em 0;
  padding: 1.5em;
  background: #1e293b;
  border-radius: 12px;
  overflow-x: auto;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.markdown-renderer :deep(pre code) {
  display: block;
  padding: 0;
  background: transparent;
  color: #e2e8f0;
  font-size: 0.95em;
  line-height: 1.6;
  border-radius: 0;
}

/* 复制按钮 */
.markdown-renderer :deep(.copy-button) {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 6px 12px;
  background: #10b981;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 0.85em;
  cursor: pointer;
  transition: all 0.2s ease;
  font-weight: 500;
  z-index: 10;
}

.markdown-renderer :deep(.copy-button:hover) {
  background: #059669;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(16, 185, 129, 0.3);
}

.markdown-renderer :deep(.copy-button.copied) {
  background: #3b82f6;
}

/* ==================== 链接样式 ==================== */
.markdown-renderer :deep(a) {
  color: #10b981;
  text-decoration: none;
  font-weight: 500;
  border-bottom: 1px solid transparent;
  transition: all 0.2s ease;
}

.markdown-renderer :deep(a:hover) {
  color: #059669;
  border-bottom-color: #10b981;
}

/* ==================== 图片样式 ==================== */
.markdown-renderer :deep(img) {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin: 1.5em 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.markdown-renderer :deep(img:hover) {
  transform: scale(1.02);
  box-shadow: 0 8px 12px rgba(0, 0, 0, 0.15);
}

/* ==================== 表格样式 ==================== */
.markdown-renderer :deep(table) {
  width: 100%;
  margin: 1.5em 0;
  border-collapse: collapse;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.markdown-renderer :deep(thead) {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
}

.markdown-renderer :deep(th) {
  padding: 12px 16px;
  text-align: left;
  font-weight: 600;
  font-size: 0.95em;
}

.markdown-renderer :deep(td) {
  padding: 12px 16px;
  border-bottom: 1px solid #e5e7eb;
}

.markdown-renderer :deep(tbody tr) {
  transition: background-color 0.2s ease;
}

.markdown-renderer :deep(tbody tr:nth-child(even)) {
  background: #f9fafb;
}

.markdown-renderer :deep(tbody tr:hover) {
  background: #f0fdf4;
}

/* ==================== 水平分割线 ==================== */
.markdown-renderer :deep(hr) {
  margin: 2em 0;
  border: none;
  height: 2px;
  background: linear-gradient(to right, transparent, #10b981, transparent);
}

/* ==================== 强调样式 ==================== */
.markdown-renderer :deep(strong) {
  font-weight: 700;
  color: #1a1a1a;
}

.markdown-renderer :deep(em) {
  font-style: italic;
  color: #4a5568;
}

/* ==================== 删除线 ==================== */
.markdown-renderer :deep(del) {
  text-decoration: line-through;
  color: #9ca3af;
}

/* ==================== 代码块滚动条美化 ==================== */
.markdown-renderer :deep(pre::-webkit-scrollbar) {
  height: 8px;
}

.markdown-renderer :deep(pre::-webkit-scrollbar-track) {
  background: #334155;
  border-radius: 4px;
}

.markdown-renderer :deep(pre::-webkit-scrollbar-thumb) {
  background: #10b981;
  border-radius: 4px;
}

.markdown-renderer :deep(pre::-webkit-scrollbar-thumb:hover) {
  background: #059669;
}

/* ==================== 响应式设计 ==================== */
@media (max-width: 768px) {
  .markdown-renderer {
    font-size: 14px;
  }

  .markdown-renderer :deep(h1) {
    font-size: 1.75em;
  }

  .markdown-renderer :deep(h2) {
    font-size: 1.5em;
  }

  .markdown-renderer :deep(h3) {
    font-size: 1.25em;
  }

  .markdown-renderer :deep(pre) {
    padding: 1em;
    border-radius: 8px;
  }

  .markdown-renderer :deep(table) {
    font-size: 0.9em;
  }

  .markdown-renderer :deep(th),
  .markdown-renderer :deep(td) {
    padding: 8px 12px;
  }
}
</style>
